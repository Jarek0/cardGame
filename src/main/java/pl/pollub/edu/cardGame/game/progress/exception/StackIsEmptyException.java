package pl.pollub.edu.cardGame.game.progress.exception;

import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

public class StackIsEmptyException extends BusinessValidationException implements ExceptionWithCode {

    private static final String STACK_IS_EMPTY = "game.progress.stack.isEmpty";

    public StackIsEmptyException() {
        super(STACK_IS_EMPTY, "Stack is empty");
    }

}
