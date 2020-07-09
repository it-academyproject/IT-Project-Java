package com.it_academyproject.exceptions;

public class CustomException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = -8671701198527177537L;

    public CustomException(String msg) {
        super(msg);
    }

}
