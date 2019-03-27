package com.example.demoFirebase.spring.conditionals;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class FirebaseConditionImpl implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String enabled = conditionContext.getEnvironment().getProperty("rs.pscode.firebase.enabled");
        return Boolean.parseBoolean(enabled);
    }
}
