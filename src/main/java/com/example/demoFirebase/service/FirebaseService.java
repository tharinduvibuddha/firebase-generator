package com.example.demoFirebase.service;

import com.example.demoFirebase.config.authFirebase.FirebaseTokenHolder;

public interface FirebaseService {

    FirebaseTokenHolder parseToken(String idToken);
}
