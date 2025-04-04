package com.ims_team4.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.thymeleaf.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Configuration
public class ResourceBundleConfig {
    private final Logger logger = Logger.getLogger(ResourceBundleConfig.class.getName());

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("lang.message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(0); // Tắt cache để reload nhanh
        messageSource.setFallbackToSystemLocale(false);
        logger.info(messageSource.toString());
        return messageSource;
    }
}
