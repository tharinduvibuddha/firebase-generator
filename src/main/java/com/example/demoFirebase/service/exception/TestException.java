package com.example.demoFirebase.service.exception;

public class TestException extends AbstractException {

    public TestException(String message, Throwable cause){
        super(message,"Test Exception", cause);
    }

    private static final long serialVersionUID = -1451835022162714730L;
}
