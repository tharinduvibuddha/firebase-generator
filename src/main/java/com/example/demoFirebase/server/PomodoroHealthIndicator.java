package com.example.demoFirebase.server;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.Random;

public class PomodoroHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode >8){
            return Health.down().withDetail("Error Code", errorCode).build();
        }

        return Health.up().build();
    }

    private int check() {
        return new Random().nextInt(10);

    }
}
