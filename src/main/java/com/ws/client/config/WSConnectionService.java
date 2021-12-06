package com.ws.client.config;

import com.ws.client.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

@Service
public class WSConnectionService {

    final Logger logger = LogManager.getLogger(WSConnectionService.class);

    @Value("${ws.endpoint}")
    String WS_ENDPOINT;

    @Value("${ws.connection-retry-interval}")
    long socketConnectionRetryInterval;

    @Autowired
    WSConnectionHandler myStompSessionHandler;

    StompSession session;

    public StompSession getSession() {
        return session;
    }

    public void setSession(StompSession session) {
        this.session = session;
    }

    public StompSession connect() throws ExecutionException, InterruptedException {
        logger.info("connecting to ws ...");
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

        stompClient.setTaskScheduler(new DefaultManagedTaskScheduler());
        ListenableFuture<StompSession> connectListener = stompClient.connect(WS_ENDPOINT, new WebSocketHttpHeaders(),
                                                                             connectHeaders, myStompSessionHandler);
        StompSession session = connectListener.get();
        logger.info("new session reset to ws");
        this.setSession(session);
        return session;
    }

    public void connectionRetry(){
        try {
            Thread.sleep(socketConnectionRetryInterval);
            this.connect();
        } catch (ExecutionException | InterruptedException e) {
            logger.info("catching the exception on retry");
        }
    }

    public void sendMessage(String topicUrl, Message message){
        synchronized (this.session){
            StompSession s = this.session;
            s.send(topicUrl, message);
        }
    }

    public void subscribe(String topicUrl){
        synchronized (this.session){
            StompSession s = this.session;
            s.subscribe("/app/subscribe-to-chat", myStompSessionHandler);
        }
    }
}
