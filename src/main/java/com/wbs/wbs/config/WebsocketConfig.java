package com.wbs.wbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.wbs.wbs.handler.AudioWebSocketHandler;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(audioWebSocketHandler(), "ws/audio")
        .addInterceptors(new HttpSessionHandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(
                ServerHttpRequest request,
                ServerHttpResponse response,
                WebSocketHandler wsHandler,
                Map<String, Object> attributes
            ) throws Exception {
                if (request instanceof ServletServerHttpRequest servletRequest) {
                    var params = servletRequest.getServletRequest().getParameterMap();
                    System.out.println("🌐 쿼리 스트링: " + servletRequest.getServletRequest().getQueryString());
        
                    if (params.containsKey("sessionId")) {
                        attributes.put("sessionId", params.get("sessionId")[0]);
                    }
                }
               
                return true; 
            }
        })
                .setAllowedOrigins("*");

    }

    @Bean
    public AudioWebSocketHandler audioWebSocketHandler(){
        return new AudioWebSocketHandler();
    }
}
