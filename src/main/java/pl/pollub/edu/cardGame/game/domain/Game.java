package pl.pollub.edu.cardGame.game.domain;

import event.game.organization.GameClosedEvent;
import event.game.organization.GameStartedEvent;
import event.game.progress.*;
import model.Card;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pollub.edu.cardGame.game.organization.connection.exception.GameIsClosedException;
import pl.pollub.edu.cardGame.game.organization.connection.exception.GameIsFullException;
import response.GameResponse;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.pollub.edu.cardGame.game.domain.GameStatus.*;
import static pl.pollub.edu.cardGame.game.domain.Player.CARDS_IN_HAND_COUNT;

@Document(collection = "Game")
public class Game {

    @Id
    private ObjectId id;

    private String founderLogin;

    private List<Player> players = new ArrayList<>();

    private CardsStack stack;

    private BattleGround battleGround;

    private GameStatus status;

    private PlayerRoleSwitcher playerRoleSwitcher = new PlayerRoleSwitcher();

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<Card> cardsCemetery = new LinkedList<>();

    public Game(String founderLogin) {
        this.founderLogin = founderLogin;
        this.players.add(new Player(founderLogin));
        this.status = OPEN;
    }

    public GameResponse toResponse() {
        return new GameResponse(id.toString(), founderLogin);
    }

    public List<GameStartedEvent> joinPlayer(String currentUserLogin) {
        this.players.add(new Player(currentUserLogin));

        this.status = STARTED;

        CardDealer cardDealer = new CardDealer(new CardsStack());
        this.stack = cardDealer.dealCards(players);

        Card trump = stack.getTrumpCard();
        playerRoleSwitcher.drawPlayerWhoStartFirst(players);
        battleGround = new BattleGround();

        return players.stream()
                .map(p -> new GameStartedEvent(p.getLogin(), id.toString(), p.getCards(), trump, p.isAttacker()))
                .collect(Collectors.toList());
    }

    public GameClosedEvent close() {
        this.status = CLOSED;
        List<String> playerLogins = this.players.stream().map(Player::getLogin).collect(Collectors.toList());
        return new GameClosedEvent(playerLogins, this.id.toString());
    }

    public List<GameFinishedEvent> finish(String winnerLogin) {
        this.status = FINISHED;
        Player lostPlayer = players.stream()
                .filter(p -> !p.getLogin().equals(winnerLogin))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Second player in game with id: " + id + " not exists!"));
        int winnerPoints = lostPlayer.getCards().stream().mapToInt(e -> e.calculateStrange(stack.getTrump())).sum();
        return players.stream()
                .map(p -> new GameFinishedEvent(id.toString(), winnerLogin, p.getLogin(), winnerPoints))
                .collect(Collectors.toList());
    }

    public boolean canAnyoneJoin() {
        if(!status.equals(OPEN)) {
            throw new GameIsClosedException(id);
        }
        if(this.players.size() > 1) {
            throw new GameIsFullException(id);
        }
        return true;
    }

    public boolean canAttack(String playerLogin) {
        Player player = getAttacker();
        return player.hasLogin(playerLogin);
    }

    public boolean canDefense(String playerLogin) {
        Player player = getDefender();
        return player.hasLogin(playerLogin);
    }

    public boolean canStopAttack(String playerLogin) {
        return canAttack(playerLogin) && !battleGround.isEmpty();
    }

    public boolean canStopDefense(String playerLogin) {
        return canDefense(playerLogin);
    }

    public boolean canCardAttack(Card attackCard) {
        return getAttacker().hasCard(attackCard) && battleGround.canCardAttack(attackCard);
    }

    public boolean canCardDefense(Card defenseCard) {
        return getDefender().hasCard(defenseCard) && battleGround.canCardDefense(defenseCard, stack.getTrump());
    }

    public PlayerAttackedEvent attack(Card attackCard) {
        Player attacker = getAttacker();
        attacker.removeCard(attackCard);
        battleGround.putCard(attackCard);
        playerRoleSwitcher.switchRoundsAfterAttack(players);
        return new PlayerAttackedEvent(id.toString(), getDefender().getLogin(), attackCard, attacker.cardsCount());
    }

    public PlayerDefendedEvent defense(Card defenseCard) {
        Player defender = getDefender();
        defender.removeCard(defenseCard);
        battleGround.putCard(defenseCard);
        playerRoleSwitcher.switchRoundsAfterDefense(players);
        return new PlayerDefendedEvent(id.toString(), getAttacker().getLogin(), defenseCard, defender.cardsCount(), battleGround.valuesOnBattleGround());
    }

    public boolean didAttackerWinBeforeAttack() {
        return stack.isEmpty() && getAttacker().hasLastCard();
    }

    public boolean didDefenderWinBeforeDefense() {
        return stack.isEmpty() && getDefender().hasLastCard();
    }

    public List<PlayerStopAttackEvent> stopAttack() {
        moveBattleCardsToCemetery();
        finishRound();
        playerRoleSwitcher.switchRoundsAfterStoppedAttack(players);
        List<PlayerStopAttackEvent> events = new ArrayList<>();
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        events.add(new PlayerStopAttackEvent(id.toString(), p1.getLogin(), p1.getCards(), p1.isAttacker(), stack.cardsCount(), p2.cardsCount()));
        events.add(new PlayerStopAttackEvent(id.toString(), p2.getLogin(), p2.getCards(), p2.isAttacker(), stack.cardsCount(), p1.cardsCount()));
        return events;
    }

    public List<PlayerStopDefenseEvent> stopDefense() {
        moveBattleCardsToLostPlayer();
        finishRound();
        playerRoleSwitcher.switchRoundsAfterStoppedDefense(players);
        List<PlayerStopDefenseEvent> events = new ArrayList<>();
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        events.add(new PlayerStopDefenseEvent(id.toString(), p1.getLogin(), p1.getCards(), p1.isAttacker(), stack.cardsCount(), p2.cardsCount()));
        events.add(new PlayerStopDefenseEvent(id.toString(), p2.getLogin(), p2.getCards(), p2.isAttacker(), stack.cardsCount(), p1.cardsCount()));
        return events;
    }

    private void moveBattleCardsToLostPlayer() {
        List<Card> cardsFromFinishBattle = battleGround.finishBattle();
        getDefender().addCards(cardsFromFinishBattle);
    }

    private void moveBattleCardsToCemetery(){
        List<Card> cardsFromFinishBattle = battleGround.finishBattle();
        cardsCemetery.addAll(cardsFromFinishBattle);
    }

    private void finishRound() {
        for(Player player : players) {
            getCardsForPlayer(player);
        }
    }

    private void getCardsForPlayer(Player player) {
        int neededCards = player.cardsCountToFullHand();
        List<Card> cardsForPlayer = stack.getCards(neededCards);
        player.addCards(cardsForPlayer);
    }

    private Player getDefender() {
        return playerRoleSwitcher.getDefender(players);
    }

    private Player getAttacker() {
        return playerRoleSwitcher.getAttacker(players);
    }

}
