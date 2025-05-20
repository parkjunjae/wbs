package com.wbs.wbs.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AudioWebSocketHandler extends BinaryWebSocketHandler {

    private final Set<WebSocketSession> clients = ConcurrentHashMap.newKeySet();
    private final Map<String, OutputStream> recordingStreams = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        String sessionId = getSessionId(session);
        clients.add(session);
        System.out.println("✅ WebSocket 연결: " + sessionId);
    
        // React가 새로 연결되면 Python에게 start 명령 전송
        if (clients.size() >= 2) {
            for (WebSocketSession s : clients) {
                if (s.isOpen() && !s.getId().equals(session.getId())) {
                    try {
                        s.sendMessage(new TextMessage("start"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        String sessionId = getSessionId(session);
        clients.remove(session);
        System.out.println("🔴 연결 종료 → 총 연결 수: " + clients.size());
        System.out.println("client disconnection " + sessionId);
        closeRecording(session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        byte[] payload = message.getPayload().array();
        String sessionId = getSessionId(session);

        if (sessionId == null || sessionId.equals(session.getId())) {
            System.out.println("❌ 유효하지 않은 sessionId, 저장 및 브로드캐스트 생략");
            return;
        }

        System.out.println("📡 현재 연결된 clients 수: " + clients.size());

    // 브로드캐스트
    for (WebSocketSession client : clients) {
        if (client.isOpen() && !client.getId().equals(session.getId())) {
            try {
                client.sendMessage(new BinaryMessage(payload));
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }

     // 저장
     OutputStream out = recordingStreams.computeIfAbsent(sessionId, id -> {
        try {
            File file = new File("uploads/audio-" + id + ".wav");
            file.getParentFile().mkdirs();
            return new BufferedOutputStream(new FileOutputStream(file, true));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    });

    try {
        out.write(payload);
    } catch (IOException e) {
        e.printStackTrace();
    }
}


    public void closeRecording(WebSocketSession session){
        String sessionId = getSessionId(session);
        OutputStream out = recordingStreams.remove(sessionId);
        if (out != null) {
            try {
                out.close();
                
                System.out.println("✅ 저장 완료: uploads/audio-" + sessionId + ".wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSessionId(WebSocketSession session) {
        Object sid = session.getAttributes().get("sessionId");
        return (sid instanceof String) ? (String) sid : session.getId(); // fallback
    }
    

}
