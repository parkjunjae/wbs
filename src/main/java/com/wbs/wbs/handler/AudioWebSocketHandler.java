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
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AudioWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> clients = ConcurrentHashMap.newKeySet();
    private final Map<String, OutputStream> recordingStreams = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        clients.add(session);
        System.out.println("clinet connected:" + session.getId());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        clients.remove(session);
        System.out.println("client disconnection" + session.getId());
        closeRecording(session);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        byte[] payload = message.getPayload().array();

        for (WebSocketSession client : clients) {
            if (client.isOpen() && !client.getId().equals(session.getId())) {
                try {
                client.sendMessage(new BinaryMessage(payload));
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        OutputStream out = recordingStreams.computeIfAbsent(session.getId(), id -> {
           try {
               File file = new File("uploads/audio-"+ id + ".raw");
               file.getParentFile().mkdirs();
               return new BufferedOutputStream(new FileOutputStream(file, true));
           }catch (IOException e) {
               throw new UncheckedIOException(e);
           }
        });

        try{
        out.write(payload);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public void closeRecording(WebSocketSession session){
        OutputStream out = recordingStreams.remove(session.getId());
        if (out != null) {
            try {
                out.close();
            }catch (IOException e) {
                e.printStackTrace();;
            }
        }
    }

}
