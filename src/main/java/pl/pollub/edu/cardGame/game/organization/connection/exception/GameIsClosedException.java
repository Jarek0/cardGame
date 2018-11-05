package pl.pollub.edu.cardGame.game.organization.connection.exception;

import org.bson.types.ObjectId;
import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

import static error.codes.ErrorCodes.GAME_IS_CLOSED;

public class GameIsClosedException extends BusinessValidationException implements ExceptionWithCode {

    public GameIsClosedException(ObjectId id) {
        super(GAME_IS_CLOSED, "Game with id: " + id + " is already closed");
    }

}
