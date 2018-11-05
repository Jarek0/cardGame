package pl.pollub.edu.cardGame.game.domain;

import event.GameClosedEvent;
import event.PlayerJoinGameEvent;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.pollub.edu.cardGame.game.organization.connection.exception.GameIsClosedException;
import pl.pollub.edu.cardGame.game.organization.connection.exception.GameIsFullException;
import response.GameResponse;

import java.util.ArrayList;
import java.util.List;

import static pl.pollub.edu.cardGame.game.domain.GameStatus.*;

@Document(collection = "Game")
public class Game {

    @Id
    private ObjectId id;

    private String founderLogin;

    private String secondPlayerLogin;

    private List<String> playersLogin = new ArrayList<>();

    private GameStatus status;

    public Game(String founderLogin) {
        this.founderLogin = founderLogin;
        this.playersLogin.add(founderLogin);
        this.status = OPEN;
    }

    public GameResponse toResponse() {
        return new GameResponse(id.toString(), founderLogin);
    }

    public PlayerJoinGameEvent joinPlayer(String currentUserLogin) {
        if(!status.equals(OPEN)) {
            throw new GameIsClosedException(id);
        }

        if(this.playersLogin.size() > 1) {
            throw new GameIsFullException(id);
        }

        this.playersLogin.add(currentUserLogin);
        this.secondPlayerLogin = currentUserLogin;

        this.status = ACCEPTED;

        return new PlayerJoinGameEvent(founderLogin, currentUserLogin, id.toString());
    }

    public GameClosedEvent close() {
        this.status = CLOSED;
        return new GameClosedEvent(this.playersLogin, this.id.toString());
    }
}
