package pl.pollub.edu.cardGame.game.organization.connection.exception;

import org.bson.types.ObjectId;
import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.ObjectNotFoundException;

import static error.codes.ErrorCodes.GAME_NOT_FOUND;

public class GameNotFoundException extends ObjectNotFoundException implements ExceptionWithCode {

    private GameNotFoundException(String msg) {
        super(GAME_NOT_FOUND, msg);
    }

    public static GameNotFoundException openGameNotFound(ObjectId gameId) {
        return new GameNotFoundException("Open game with id: " + gameId + " not found");
    }

    public static GameNotFoundException startedGameNotFound(ObjectId gameId) {
        return new GameNotFoundException("Started game with id: " + gameId + " not found");
    }

}
