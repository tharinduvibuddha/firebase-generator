package com.example.demoFirebase.service;

import com.example.demoFirebase.domain.model.TestEntity;

import java.util.Collection;

public interface TestService {

    Collection <TestEntity> findAll();
    TestEntity create(String name);

}
