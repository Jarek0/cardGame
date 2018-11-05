package pl.pollub.edu.cardGame.game.organization.connection.exception;

import org.bson.types.ObjectId;
import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

import static error.codes.ErrorCodes.GAME_IS_FULL;

public class GameIsFullException extends BusinessValidationException implements ExceptionWithCode {


    public GameIsFullException(ObjectId id) {
        super(GAME_IS_FULL, "Game with id: " + id + " is already full");
    }

}
