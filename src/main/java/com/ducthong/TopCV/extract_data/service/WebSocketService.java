package com.ducthong.TopCV.extract_data.service;

import com.ducthong.TopCV.extract_data.dto.CvProgressMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendProgress(String cvId, String status, String sessionId, int total, int done) {
        CvProgressMessage msg = new CvProgressMessage(cvId, status, total, done);
        messagingTemplate.convertAndSend("/topic/cv-status/" + sessionId, msg);
    }
}