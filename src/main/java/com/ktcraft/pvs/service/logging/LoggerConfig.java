package com.ktcraft.pvs.service.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LoggerConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Logger createLogger(final InjectionPoint injectionPoint) {
        final Class<?> c = injectionPoint.getMember().getDeclaringClass();
        return LoggerFactory.getLogger(c);
    }
}
