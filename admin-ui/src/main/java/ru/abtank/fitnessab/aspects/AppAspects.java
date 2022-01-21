package ru.abtank.fitnessab.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AppAspects {

    private final static Logger LOG = LoggerFactory.getLogger(AppAspects.class);

    // * - ?
    // * - класс
    // * - имя метода
    // (..) - параметры методв
    @Before("execution(* ru.abtank.fitnessab.controllers.*.*(..))")
    public void before (JoinPoint joinPoint){
        LOG.info("Colled method: "+joinPoint);
    }
}
