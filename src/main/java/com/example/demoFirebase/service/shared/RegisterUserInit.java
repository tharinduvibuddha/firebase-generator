package com.example.demoFirebase.service.shared;

public class RegisterUserInit {

    private final String userName;
    private final String email;

    public RegisterUserInit(String userName ,String email){
        super();
        this.email = email;
        this.userName = userName;

    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }
}
