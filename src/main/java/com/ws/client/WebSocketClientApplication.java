package com.ws.client;

import com.ws.client.socket.WebSocketClientConnection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class WebSocketClientApplication {

	final String WS_ENDPOINT = "ws://localhost:8787/portfolio";

	public static void main(String[] args) {
		SpringApplication.run(WebSocketClientApplication.class, args);
	}

	@Bean
	public StompSession getSocketConnection() throws ExecutionException, InterruptedException {
		return WebSocketClientConnection.connect(WS_ENDPOINT);
	}
}