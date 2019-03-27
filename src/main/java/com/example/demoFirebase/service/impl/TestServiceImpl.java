package com.example.demoFirebase.service.impl;

import com.example.demoFirebase.config.SecurityConfig.Roles;
import com.example.demoFirebase.domain.dao.TestRepository;
import com.example.demoFirebase.domain.model.TestEntity;
import com.example.demoFirebase.service.TestService;
import com.example.demoFirebase.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class TestServiceImpl implements TestService {

   private final static String COUNTER_TEST = "rs.pscode.entity.test";

   @Autowired
   private TestRepository testRepository;

   @Autowired
   private CounterService counterService;




    @Transactional
    @Secured(value = Roles.ROLE_USER)
   public Collection<TestEntity> findAll(){
        return testRepository.findAll();


    }


    @Transactional
    @Secured(value = Roles.ROLE_USER)
    public TestEntity create(String name) {
        if (StringUtil.isBlank(name)){
        throw new IllegalArgumentException("TestNameIsBlank");

    }
    counterService.increment(COUNTER_TEST + "Created");
        TestEntity entity = new TestEntity(name);
        return testRepository.save(entity);


}

}
