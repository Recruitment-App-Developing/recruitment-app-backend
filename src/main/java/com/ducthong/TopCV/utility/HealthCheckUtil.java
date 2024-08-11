package com.ducthong.TopCV.utility;

import java.net.InetAddress;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HealthCheckUtil implements HealthIndicator {
    @Override
    public Health health() {
        try {
            String computerName = InetAddress.getLocalHost().getHostName();
            return Health.up().withDetail("computerName", computerName).build();
        } catch (Exception e) {
            return Health.down().withDetail("Error", e.getMessage()).build();
        }
    }
}
