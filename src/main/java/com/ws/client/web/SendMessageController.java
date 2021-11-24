package com.ws.client.web;

import com.ws.client.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
