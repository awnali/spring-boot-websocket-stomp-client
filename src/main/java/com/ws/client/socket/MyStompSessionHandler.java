package com.ws.client.socket;

import com.ws.client.ApplicationContextProvider;
import com.ws.client.WebSocketClientApplication;
import com.ws.client.model.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

@Service
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    final Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

    final WebSocketClientConnection ws = new WebSocketClientConnection();
    final String WS_ENDPOINT = "ws://localhost:8787/portfolio";

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/messages", this);
        logger.info("Subscribed to /topic/messages");
//
//        session.subscribe("/user/queue/messages", this);
//        logger.info("Subscribed to /user/queue/chat");
        session.send("/app/greeting", getSampleMessage());
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
                                Throwable exception) {
        logger.error("Got an exception");

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        logger.error("Got transport exception");

        try {
            Thread.sleep(10000);

            logger.info("after sleep");
            ApplicationContextProvider.getApplicationContext()
                                      .getAutowireCapableBeanFactory()
                                      .autowireBean(ws);
            ws.connect(WS_ENDPOINT);
        } catch (ExecutionException | InterruptedException e) {
            logger.info("catching the exception on retry");
        }
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return Message.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = (Message) payload;
        logger.info("Received : " + msg.getText() + " from : " + msg.getFrom());
    }

    private Message getSampleMessage() {
        Message msg = new Message();
        msg.setFrom("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }

}
