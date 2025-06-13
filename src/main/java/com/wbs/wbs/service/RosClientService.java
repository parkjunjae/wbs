package com.wbs.wbs.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbs.wbs.dto.BoundaryDto;


@Service
public class RosClientService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public void sendBoundaryToRos(BoundaryDto boundaryDto){
        try {
            String json = objectMapper.writeValueAsString(boundaryDto);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:5000/ros/coverage"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

                httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        System.out.println("ros 응답" + response.body());
                    });
                    
        }catch(Exception e) {
            System.out.println(" 로스 전송 실패 " + e.getMessage());
        }

    }
    
}
