package com.example.project_jjol.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.project_jjol.model.User;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    // WebSocket 세션을 관리하기 위한 리스트
    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트로부터 수신한 메시지 처리
        String payload = message.getPayload();
        log.info("Received message: " + payload);

        // 연결된 모든 WebSocket 세션에게 메시지 전송
        for (WebSocketSession sess : list) {
            sess.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 WebSocket 연결을 맺었을 때 호출됩니다.
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");

        if (httpSession != null) {
            // HTTP 세션에서 로그인한 사용자 정보를 가져옵니다.
            User loggedInUser = (User) httpSession.getAttribute("loggedInUser");

            if (loggedInUser != null) {
                // WebSocket 세션을 리스트에 추가하여 관리합니다.
                list.add(session);
                log.info(session + " 클라이언트 접속");
            } else {
                // 로그인한 사용자 정보가 없으면 연결을 거부하고 세션을 닫습니다.
                session.close(CloseStatus.NOT_ACCEPTABLE.withReason("No logged in user"));
            }
        } else {
            // HTTP 세션이 없으면 연결을 거부하고 세션을 닫습니다.
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("No HTTP session"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 클라이언트가 WebSocket 연결을 해제했을 때 호출됩니다.
        log.info(session + " 클라이언트 접속 해제");

        // 리스트에서 해당 WebSocket 세션을 제거합니다.
        list.remove(session);
    }
}
