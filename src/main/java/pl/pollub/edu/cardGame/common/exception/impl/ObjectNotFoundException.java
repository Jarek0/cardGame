package pl.pollub.edu.cardGame.common.exception.impl;


import pl.pollub.edu.cardGame.common.exception.ExceptionWithCode;

public class ObjectNotFoundException extends IllegalArgumentException implements ExceptionWithCode {

    private String code;

    public ObjectNotFoundException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    @Override
    public String code() {
        return code;
    }
}
