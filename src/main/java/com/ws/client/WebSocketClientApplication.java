package com.ws.client;

import com.ws.client.socket.WSConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class WebSocketClientApplication {

    @Autowired
    WSConnectionService ws;

    public static void main(String[] args) {
        SpringApplication.run(WebSocketClientApplication.class, args);
    }

    @Bean
    public StompSession getSocketConnection() throws ExecutionException, InterruptedException {
        return ws.connect();
    }
}
