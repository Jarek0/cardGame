package pl.pollub.edu.cardGame.game.play;

import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

public class StackIsEmptyException extends BusinessValidationException implements ExceptionWithCode {

    private static final String STACK_IS_EMPTY = "game.play.stack.isEmpty";

    public StackIsEmptyException() {
        super(STACK_IS_EMPTY, "Strack is empty");
    }

}
