package com.example.demoFirebase.domain.dao;

import com.example.demoFirebase.domain.model.TestEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface TestRepository extends CrudRepository<TestEntity,Long> {
    Collection<TestEntity> findAll();
}
