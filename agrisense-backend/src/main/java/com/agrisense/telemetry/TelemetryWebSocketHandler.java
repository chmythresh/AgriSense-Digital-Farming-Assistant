package com.agrisense.telemetry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TelemetryWebSocketHandler extends TextWebSocketHandler {

    private final TelemetryController telemetry;
    private final ObjectMapper objectMapper;
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    public TelemetryWebSocketHandler(TelemetryController telemetry, ObjectMapper objectMapper) {
        this.telemetry = telemetry;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        session.sendMessage(new TextMessage(serializeSnapshot()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Scheduled(fixedRate = 5000)
    public void broadcastTelemetry() {
        if (sessions.isEmpty()) {
            return;
        }
        try {
            TextMessage message = new TextMessage(serializeSnapshot());
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(message);
                } else {
                    sessions.remove(session);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to broadcast telemetry", exception);
        }
    }

    private String serializeSnapshot() {
        try {
            return objectMapper.writeValueAsString(telemetry.snapshot());
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to serialize telemetry snapshot", exception);
        }
    }
}
