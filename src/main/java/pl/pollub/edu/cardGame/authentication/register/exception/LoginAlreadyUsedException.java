package pl.pollub.edu.cardGame.authentication.register.exception;

import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;
import pl.pollub.edu.cardGame.common.exception.impl.BusinessValidationException;

import static error.codes.ErrorCodes.LOGIN_ALREADY_USED;

public class LoginAlreadyUsedException extends BusinessValidationException implements ExceptionWithCode {

    public LoginAlreadyUsedException(String login) {
        super(LOGIN_ALREADY_USED, "Login : " + login + " is already used");
    }

}
