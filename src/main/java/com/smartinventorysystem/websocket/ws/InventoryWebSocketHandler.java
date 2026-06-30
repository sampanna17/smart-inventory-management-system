package com.smartinventorysystem.websocket.ws;

import org.jspecify.annotations.NonNull;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

@Component
public class InventoryWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket Client Connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.@NonNull CloseStatus status) {
        System.out.println("WebSocket Client Disconnected: " + session.getId());
    }
}