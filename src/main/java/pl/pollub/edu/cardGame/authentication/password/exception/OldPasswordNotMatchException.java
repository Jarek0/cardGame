package pl.pollub.edu.cardGame.authentication.password.exception;

import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

import static error.codes.ErrorCodes.PASSWORD_NOT_MATCH;

public class OldPasswordNotMatchException extends BusinessValidationException implements ExceptionWithCode {

    public OldPasswordNotMatchException() {
        super(PASSWORD_NOT_MATCH, "Old password not match");
    }

}
