package pl.pollub.edu.cardGame.common.exception.impl;


import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;

public class BusinessValidationException extends IllegalArgumentException implements ExceptionWithCode {

    private String code;

    public BusinessValidationException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }

}
