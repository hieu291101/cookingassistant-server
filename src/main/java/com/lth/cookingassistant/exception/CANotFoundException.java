package com.lth.cookingassistant.exception;

public class CANotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public CANotFoundException() {
        super();
    }

    public CANotFoundException(String message) {
        super(message);
    }
}
