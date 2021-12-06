package com.ws.client;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        context = arg0;

    }

    public Object getBean(String name){
        return context.getBean(name, Object.class);
    }

    public void addBean(String beanName, Object beanObject){
        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)context).getBeanFactory();
        beanFactory.registerSingleton(beanName, beanObject);
    }

    public void removeBean(String beanName){
        BeanDefinitionRegistry reg = (BeanDefinitionRegistry) context.getAutowireCapableBeanFactory();
        reg.removeBeanDefinition(beanName);
    }
}
