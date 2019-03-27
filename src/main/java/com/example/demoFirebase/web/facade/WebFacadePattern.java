package com.example.demoFirebase.web.facade;

import com.example.demoFirebase.web.dto.test.TestJson;
import com.example.demoFirebase.web.dto.test.TestRsquestJson;

import java.util.List;

public interface WebFacadePattern {
    void registerUser(String firebaseToken);
    TestJson createTest(TestRsquestJson json);
    List<TestJson> getTaskList();


}
