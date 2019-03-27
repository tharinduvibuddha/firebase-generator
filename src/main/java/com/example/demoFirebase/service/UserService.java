package com.example.demoFirebase.service;

import com.example.demoFirebase.domain.model.UserEntity;
import com.example.demoFirebase.service.shared.RegisterUserInit;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserEntity registerUser(RegisterUserInit init);
}
