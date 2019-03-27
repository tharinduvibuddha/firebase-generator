package com.example.demoFirebase.service.exception;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

public class FirebaseUserNotExistsException extends AuthenticationCredentialsNotFoundException {
    public FirebaseUserNotExistsException() {
        super("User Not Exsist");


    }

    private static final long serialVersionUID = 789949671713648425L;
}
