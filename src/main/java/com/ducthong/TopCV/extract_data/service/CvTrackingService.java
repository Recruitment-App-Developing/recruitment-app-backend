package com.ducthong.TopCV.extract_data.service;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CvTrackingService {
    private static final Logger logger = LoggerFactory.getLogger(CvTrackingService.class);

    private final Map<String, AtomicInteger> completedMap = new ConcurrentHashMap<>();
    private final Map<String, Integer> totalMap = new ConcurrentHashMap<>();

    public void registerSession(String sessionId, int totalFiles) {
        totalMap.put(sessionId, totalFiles);
        completedMap.put(sessionId, new AtomicInteger(0));
    }

    public int increment(String sessionId) {
        AtomicInteger counter = completedMap.get(sessionId);
        if (counter == null) {
            logger.warn("Session '{}' chưa được đăng ký. Tự động khởi tạo với total = 0", sessionId);
            // Tùy chọn: tự động khởi tạo nếu chưa có
            registerSession(sessionId, 0);
            counter = completedMap.get(sessionId);
        }
        return counter.incrementAndGet();
    }

    public int getTotal(String sessionId) {
        return totalMap.getOrDefault(sessionId, 0);
    }

    public void clear(String sessionId) {
        completedMap.remove(sessionId);
        totalMap.remove(sessionId);
    }
}
