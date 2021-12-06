package com.ws.client;

import com.ws.client.socket.WebSocketClientConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class WebSocketClientApplication {

    @Autowired
    WebSocketClientConnection ws;

    public static void main(String[] args) {
        SpringApplication.run(WebSocketClientApplication.class, args);
    }

    @Bean
    public StompSession getSocketConnection() throws ExecutionException, InterruptedException {
        return ws.connect();
    }
}
