package com.ws.client.socket;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import javax.websocket.ClientEndpoint;
import java.util.concurrent.ExecutionException;

@ClientEndpoint
public class WebSocketClientConnection {

    public static StompSession connect(String endpointURI) throws ExecutionException, InterruptedException {
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        StompHeaders connectHeaders =  new StompHeaders();

        // user id 48
        connectHeaders.add("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0OCIsImV4cCI6MTYzNzg5MjA0NH0._C11kvuMq_p5gOgxqWLSRjOpx_eshfORCiB3zVvFI3CWCxA1SN1h5BpbSsBvYpB3fqaOtoIOgcI-JfFcka6JIw");
        // user id 2
//        connectHeaders.add("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiZXhwIjoxNjM3OTkwNDQyfQ.tzgYEjZqBKCKP7OjAL2cT45dWJKy8uSYydWpZ_Ou5a8ayN4yxuaHICxb8g4SG9Wzt2jPAHXkVGo3kZpf6py3Yw");

        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        ListenableFuture<StompSession> connectListener = stompClient.connect(endpointURI, new WebSocketHttpHeaders(), connectHeaders, sessionHandler);

        return connectListener.get();

//        stompClient.connect(endpointURI, sessionHandler);


        //new Scanner(System.in).nextLine(); // Don't close immediately.
    }

}
