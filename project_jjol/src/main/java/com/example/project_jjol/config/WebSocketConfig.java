package com.example.project_jjol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.example.project_jjol.handler.ChatHandler;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Configuration
@RequiredArgsConstructor // Lombok 어노테이션으로 생성자 주입을 자동으로 처리합니다.
@EnableWebSocket // WebSocket을 활성화합니다.
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler; // ChatHandler를 주입받습니다.

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // "/ws/chat" 경로로 들어오는 WebSocket 요청을 ChatHandler를 사용하여 처리합니다.
        registry.addHandler(chatHandler, "/ws/chat")
                // HttpSessionHandshakeInterceptor를 추가하여 WebSocket 접속 시 HTTP 세션을 처리합니다.
                .addInterceptors(new HttpSessionHandshakeInterceptor() {
                    @Override
                    public void afterHandshake(
                        ServerHttpRequest request,
                        ServerHttpResponse response,
                        WebSocketHandler wsHandler,
                        Exception ex) {
                            super.afterHandshake(request, response, wsHandler, ex);
                    }

                    @Override
                    public boolean beforeHandshake(
                        ServerHttpRequest request,
                        ServerHttpResponse response,
                        WebSocketHandler wsHandler,
                        Map<String, Object> attributes) throws Exception {
                            // HTTP 요청이 ServletServerHttpRequest인 경우에만 실행됩니다.
                            if (request instanceof ServletServerHttpRequest) {
                                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                                // HTTP 요청에서 세션을 가져와서 attributes 맵에 "HTTP_SESSION" 키로 저장합니다.
                                HttpSession session = servletRequest.getServletRequest().getSession(false);
                                if (session != null) {
                                    attributes.put("HTTP_SESSION", session);
                                }
                            }
                            return true; // handshake 절차를 통과하도록 합니다.
                    }
                })
                // 모든 origin에서의 접속을 허용합니다.
                .setAllowedOrigins("*");
    }
}
