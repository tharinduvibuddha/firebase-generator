package com.example.demoFirebase.service.shared;

import com.example.demoFirebase.config.authFirebase.FirebaseTokenHolder;
import com.example.demoFirebase.service.exception.FirebaseTokenInvalidException;
import com.example.demoFirebase.util.StringUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.Task;
import com.google.firebase.tasks.Tasks;

public class FirebaseParser {
    public FirebaseTokenHolder parseToken(String idToken){
        if(StringUtil.isBlank(idToken)){
            throw new IllegalArgumentException("Firebase Token Blank");
        }
        try {
            Task<FirebaseToken> authTask = FirebaseAuth.getInstance().verifyIdToken(idToken);

            Tasks.await(authTask);
            return new FirebaseTokenHolder(authTask.getResult());
        }catch (Exception e){
            throw new FirebaseTokenInvalidException(e.getMessage());
        }
    }
}
