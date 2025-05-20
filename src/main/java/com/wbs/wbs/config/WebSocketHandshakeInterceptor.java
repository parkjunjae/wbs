package com.wbs.wbs.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpServletRequest; // ✅ 누락되어 있던 import

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
     public boolean beforeHandshake(ServerHttpRequest request,
                                   org.springframework.http.server.ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) {
         if (request instanceof ServletServerHttpRequest) {
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        String sessionId = servletRequest.getParameter("sessionId");
        if (sessionId != null) {
            attributes.put("sessionId", sessionId);
            System.out.println("🌐 세션 ID 수신: " + sessionId);
        }
    }

    return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               org.springframework.http.server.ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {


                                
                               }


    
}
