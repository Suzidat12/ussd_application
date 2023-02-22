package com.zik.ussd_application.exception;

public class InsufficientException extends IllegalArgumentException{
    private String message;
    public InsufficientException(String message){
        super(message);
        this.message=message;
    }
    public InsufficientException(){

    }

}
