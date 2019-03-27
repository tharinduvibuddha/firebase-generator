package com.example.demoFirebase.domain.dao;

import com.example.demoFirebase.domain.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,Long> {

UserEntity findByUsername(String username);



}
