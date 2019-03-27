package com.example.demoFirebase.config.authFirebase;

import com.example.demoFirebase.service.exception.FirebaseUserNotExistsException;
import com.example.demoFirebase.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class FirebaseAuthProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier(value = UserServiceImpl.NAME)
    private UserDetailsService userService;



    @Override
    public boolean supports(Class<?> authentication) {
        return (FirebaseAuthToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate (Authentication authentication) throws AuthenticationException{
        if (!supports(authentication.getClass())){
            return null;
        }

        FirebaseAuthToken firebaseAuthToken = (FirebaseAuthToken) authentication;
        UserDetails details = userService.loadUserByUsername(firebaseAuthToken.getName());
        if (details == null){
            throw new FirebaseUserNotExistsException();
        }

        firebaseAuthToken = new FirebaseAuthToken(details ,authentication.getCredentials(),details.getAuthorities());

        return firebaseAuthToken;
    }
}