package com.ims_team4.utils.webSocket;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContext implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @NotNull
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
