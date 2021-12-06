package com.ws.client.web;

import com.ws.client.model.Message;
import com.ws.client.socket.MyStompSessionHandler;
import com.ws.client.socket.WebSocketClientConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class SendMessageController {

    @Autowired
    WebSocketClientConnection con;

    @GetMapping("/user-only")
    public String fireGreeting() {
        System.out.println("Message should only go to this user, who is sending the message");
        StompSession t = con.getSession();
        synchronized (con) {
            t.send("/app/chat", getSampleMessage());
        }

        return "Sent";
    }

    @GetMapping("/broadcast")
    public String fireBroadcast() {
        System.out.println("Fire broadcast: Reply should go to every user which is subscribed to /topic/messages");
        StompSession t = con.getSession();
        synchronized (con) {
            t.send("/app/greeting", getSampleMessage());
        }
        return "Sent";
    }

    @GetMapping("/to-specific-user/{userId}")
    public String fireToSpecificUser(@PathVariable String userId) {
        System.out.println("Message should only go to user with UserId ");
        StompSession t = con.getSession();
        synchronized (con) {
            t.send("/app/chat-with-user/" + userId, getSampleMessage());
        }
        return "Sent";
    }

    @GetMapping("/request-reply")
    public String requestReply() {
        System.out.println("Fire request/reply: reply will come to user who sent the message");
        StompSession t = con.getSession();
        synchronized (con) {
            t.subscribe("/app/subscribe-to-chat", new MyStompSessionHandler());
        }
        return "Sent";
    }


    private Message getSampleMessage() {
        Message msg = new Message();
        msg.setFrom("Nicky");
        msg.setText("Howdy!!");
        return msg;
    }
}
