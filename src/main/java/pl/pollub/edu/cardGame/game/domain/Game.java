package pl.pollub.edu.cardGame.game.domain;

import event.GameClosedEvent;
import event.GameStartedEvent;
import model.Card;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pollub.edu.cardGame.game.domain.card.CardDealer;
import pl.pollub.edu.cardGame.game.domain.card.CardStack;
import pl.pollub.edu.cardGame.game.organization.connection.exception.GameIsClosedException;
import pl.pollub.edu.cardGame.game.organization.connection.exception.GameIsFullException;
import response.GameResponse;

import java.util.ArrayList;
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

    private GameStatus status;

    public Game(String founderLogin) {
        this.founderLogin = founderLogin;
        this.players.add(new Player(founderLogin));
        this.status = OPEN;
    }

    public GameResponse toResponse() {
        return new GameResponse(id.toString(), founderLogin);
    }

    public List<GameStartedEvent> joinPlayer(String currentUserLogin) {
        validateCanJoin();
        this.players.add(new Player(currentUserLogin));

        this.status = STARTED;

        CardDealer cardDealer = new CardDealer(new CardStack());
        this.stack = cardDealer.dealCards(players);

        Card trump = stack.getTrumpCard();

        return players.stream()
                .map(p -> new GameStartedEvent(p.getLogin(), id.toString(), p.getCards(), trump))
                .collect(Collectors.toList());
    }

    public GameClosedEvent close() {
        this.status = CLOSED;
        List<String> playerLogins = this.players.stream().map(Player::getLogin).collect(Collectors.toList());
        return new GameClosedEvent(playerLogins, this.id.toString());
    }

    private void validateCanJoin() {
        if(!status.equals(OPEN)) {
            throw new GameIsClosedException(id);
        }
        if(this.players.size() > 1) {
            throw new GameIsFullException(id);
        }
    }
}
