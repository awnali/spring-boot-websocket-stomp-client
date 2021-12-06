package com.ws.client;

import com.ws.client.socket.MyService;
import com.ws.client.socket.WebSocketClientConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.stomp.DefaultStompSession;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class WebSocketClientApplication {

    @Value("${ws.endpoint}")
    String WS_ENDPOINT;

    @Autowired
    MyService service;

    public static void main(String[] args) {
        SpringApplication.run(WebSocketClientApplication.class, args);
    }

    @Bean
    public static ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }

    @Bean(name = "StompSession")
    public StompSession getSocketConnection() throws ExecutionException, InterruptedException {
        return service.connect();
    }


    @EventListener(ApplicationReadyEvent.class)
    void test() throws ExecutionException, InterruptedException {

//		ConfigurableApplicationContext c = ((ConfigurableApplicationContext)ApplicationContextProvider
//		.getApplicationContext());
//		ConfigurableListableBeanFactory beanFactory = c.getBeanFactory();
//		StompSession s= (StompSession) beanFactory.getBean(StompSession.class.getName());

//        DefaultStompSession s =
//				(DefaultStompSession) ApplicationContextProvider.getApplicationContext().getBean(StompSession.class);
//        System.out.println(s.getSessionId());
//
//        s.disconnect();
//
//        ConfigurableApplicationContext ctx =
//				((ConfigurableApplicationContext) ApplicationContextProvider.getApplicationContext());
//        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getAutowireCapableBeanFactory();
//        registry.removeBeanDefinition("StompSession");
//
//        StompSession s1=WebSocketClientConnection.getInstance().connect("ws://localhost:8787/portfolio");
//        SingletonBeanRegistry beanRegistry = ctx.getBeanFactory();
//        beanRegistry.registerSingleton("StompSession", s1);
//
//
//        s = (DefaultStompSession) ApplicationContextProvider.getApplicationContext().getBean(StompSession.class);
//        System.out.println(s.getSessionId());

//		ctx.refresh();

//		beanFactory.destroyBean(StompSession.class.getName());
//		c.refresh();
//        s1.disconnect();
//
//        StompSession s=webSocketClientConnection.connect("ws://localhost:8787/portfolio");
//        ConfigurableApplicationContext ctx =
//                ((ConfigurableApplicationContext) ApplicationContextProvider.getApplicationContext());
////
//        GenericBeanDefinition bd = new GenericBeanDefinition();
//        bd.setBeanClass(DefaultStompSession.class);
//        bd.getPropertyValues().add("StompSession", s);
//        ((DefaultListableBeanFactory) ctx.getBeanFactory())
//                .registerBeanDefinition("StompSession", bd);

//        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getAutowireCapableBeanFactory();
//        registry.removeBeanDefinition("StompSession");
//
//        DefaultListableBeanFactory beanFactory = ((DefaultListableBeanFactory) ctx.getBeanFactory());
//        beanFactory.initializeBean(s,"StompSession"); //could be class' canonical name
////        beanFactory.autowireBeanProperties(s, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
//        beanFactory.registerSingleton("StompSession", s);
    }
}
