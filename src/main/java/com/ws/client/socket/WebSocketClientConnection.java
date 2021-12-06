package com.ws.client.socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.websocket.ClientEndpoint;
import java.util.concurrent.*;

@Service
public class WebSocketClientConnection {

    final Logger logger = LogManager.getLogger(WebSocketClientConnection.class);

    @Autowired
    WebSocketMessageService service;

    public StompSession connect(String endpointURI) throws ExecutionException, InterruptedException {
        logger.info("connecting ...");
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompHeaders connectHeaders = new StompHeaders();

        // user id 48
        // connectHeaders.add("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0OCIsImV4cCI6MTYzNzg5MjA0NH0
        // ._C11kvuMq_p5gOgxqWLSRjOpx_eshfORCiB3zVvFI3CWCxA1SN1h5BpbSsBvYpB3fqaOtoIOgcI-JfFcka6JIw");
        // user id 2
        connectHeaders.add("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjM4OTE5MDM5fQ" +
                ".hyCBal7tnYlM34LF7RDGCD4oQuuzxgl3J0uDJJ94bsvGe9dHEyQRVXHdfBoxetAjxf-P2c6M2TeKscLfR8mU4Q");

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        stompClient.setTaskScheduler(new DefaultManagedTaskScheduler());
        logger.info("just before connecting");
        ListenableFuture<StompSession> connectListener = stompClient.connect(endpointURI, new WebSocketHttpHeaders(),
                                                                             connectHeaders, sessionHandler);
        StompSession session = connectListener.get();
        logger.info("new session reset");
        service.setSession(session);
        return session;
    }
}
