package com.ws.client.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class MyService {

    @Autowired
    WebSocketClientConnection service;

    String WS_ENDPOINT = "ws://localhost:8787/portfolio";

    public StompSession connect() throws ExecutionException, InterruptedException {
        return service.connect(WS_ENDPOINT);
    }
}
