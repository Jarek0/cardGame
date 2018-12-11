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

@Document(collection = "Game")
public class Game {

    @Id
    private ObjectId id;

    private String founderLogin;

    private List<Player> players = new ArrayList<>();

    private CardStack stack;

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

        CardDealer cardDealer = new CardDealer(new CardStack());
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

    public List<GameFinishedEvent> finish(String winnerLogin) { //change winnerLogin
        this.status = FINISHED;
        return players.stream()
                .map(p -> new GameFinishedEvent(id.toString(), winnerLogin, p.getLogin()))
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
        return new PlayerAttackedEvent(id.toString(), getDefender().getLogin(), attackCard);
    }

    public PlayerDefendedEvent defense(Card defenseCard) {
        getDefender().removeCard(defenseCard);
        battleGround.putCard(defenseCard);
        playerRoleSwitcher.switchRoundsAfterDefense(players);
        return new PlayerDefendedEvent(id.toString(), getAttacker().getLogin(), defenseCard);
    }

    public boolean didAttackerWonBeforeAttack() {
        return stack.isEmpty() && getAttacker().hasLastCard();
    }

    public boolean didDefenderWonBeforeDefense() {
        return stack.isEmpty() && getDefender().hasLastCard();
    }

    public boolean didDefenderWonBeforeStopAttack() {
        return attackerNeedMoreOrEqCardsThanStack() && deffenderHaveLessCardsThanStackAndAttacker();
    }

    public boolean didAttackerWonBeforeStopDefense() {
        return attackerNeedMoreOrEqCardsThanStack() && deffenderHaveMoreCardsThanStackAndAttacker();
    }

    public List<PlayerStopAttackEvent> stopAttack() {
        finishRound();
        playerRoleSwitcher.switchRoundsAfterStoppedAttack(players);
        return players.stream()
                .map(p -> new PlayerStopAttackEvent(id.toString(), p.getLogin(), p.getCards(), p.isAttacker()))
                .collect(Collectors.toList());
    }

    public List<PlayerStopDefenseEvent> stopDefense() {
        finishRound();
        playerRoleSwitcher.switchRoundsAfterStoppedDefense(players);
        return players.stream()
                .map(p -> new PlayerStopDefenseEvent(id.toString(), p.getLogin(), p.getCards(), p.isAttacker()))
                .collect(Collectors.toList());
    }

    private void finishRound() {
        cardsCemetery.addAll(battleGround.finishBattle());

        Player attacker = getAttacker();
        List<Card> cardsForAttacker = stack.getCards(attacker);
        attacker.addCards(cardsForAttacker);

        Player defender = getDefender();
        List<Card> cardsForDefender = stack.getCards(defender);
        attacker.addCards(cardsForDefender);
    }

    private Player getDefender() {
        return playerRoleSwitcher.getDefender(players);
    }

    private Player getAttacker() {
        return playerRoleSwitcher.getAttacker(players);
    }

    private boolean deffenderHaveLessCardsThanStackAndAttacker() {
        return getDefender().cardsCount() < getAttacker().cardsCountToFullHand() + stack.cardsCount();
    }

    private boolean deffenderHaveMoreCardsThanStackAndAttacker() {
        return getDefender().cardsCount() > getAttacker().cardsCountToFullHand() + stack.cardsCount();
    }

    private boolean attackerNeedMoreOrEqCardsThanStack() {
        return getAttacker().cardsCountToFullHand() <= stack.cardsCount();
    }
}
