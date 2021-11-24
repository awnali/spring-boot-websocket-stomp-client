package com.ws.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.AbstractMessageSendingTemplate;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.DefaultStompSession;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@RestController
@RequestMapping("/message")
public class SendMessageController {

    @Autowired
    private StompSession template;

    @GetMapping("/send")
    public Message fireGreeting() {
        System.out.println("Fire");
        template.send("/app/greeting", getSampleMessage());
        return getSampleMessage();
    }

    private Message getSampleMessage() {
        Message msg = new Message();
        msg.setFrom("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }
}
