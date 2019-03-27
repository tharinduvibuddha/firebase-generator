package com.example.demoFirebase.web.facade;

import com.example.demoFirebase.config.SecurityConfig.Roles;
import com.example.demoFirebase.config.authFirebase.FirebaseTokenHolder;
import com.example.demoFirebase.domain.model.TestEntity;
import com.example.demoFirebase.service.FirebaseService;
import com.example.demoFirebase.service.TestService;
import com.example.demoFirebase.service.UserService;
import com.example.demoFirebase.service.impl.UserServiceImpl;
import com.example.demoFirebase.service.shared.RegisterUserInit;
import com.example.demoFirebase.util.StringUtil;
import com.example.demoFirebase.web.config.WebConfig;
import com.example.demoFirebase.web.dto.test.TestJson;
import com.example.demoFirebase.web.dto.test.TestRsquestJson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;

import javax.transaction.Transactional;
import java.util.List;

public class WebFacadePatternImpl implements WebFacadePattern {

    @Autowired(required = false)
    private FirebaseService firebaseService;

    @Autowired
    private TestService testService;

    @Autowired
    @Qualifier(value = UserServiceImpl.NAME)
    private UserService userService;

    @Autowired
    @Qualifier(WebConfig.MODEL_MAPPER)
    private ModelMapper modelMapper;

    @Transactional
    @Override
    @Secured(value = Roles.ROLE_USER)
    public void registerUser(String firebaseToken) {
        if(StringUtil.isBlank(firebaseToken)){
            throw  new IllegalArgumentException("Firebase Token Blank");
        }
        FirebaseTokenHolder tokenHolder = firebaseService.parseToken(firebaseToken);
        userService.registerUser(new RegisterUserInit(tokenHolder.getUid(),tokenHolder.getEmail()));

    }


    @Transactional
    @Override
    @Secured(value = Roles.ROLE_USER)
    public TestJson createTest(TestRsquestJson json) {
        TestEntity testEntity = testService.create(json.getName());
        return modelMapper.map(testEntity, TestJson.class);
    }

    @Override
    public List<TestJson> getTaskList() {
        return null;
    }
}
