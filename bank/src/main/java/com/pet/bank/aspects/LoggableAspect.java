package com.pet.bank.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@Slf4j
public class LoggableAspect {

    private static final ThreadLocal<List<String>> threadLocalLogs = ThreadLocal.withInitial(ArrayList::new);

    @Pointcut("@annotation(Loggable)")
    public void loggableMethods(){
    }

    @Around("loggableMethods()")
    public Object collectLogs(ProceedingJoinPoint joinPoint){
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void addLogMessage(String message) {
        threadLocalLogs.get().add(message);
    }

    public static List<String> getLogs() {
        return new ArrayList<>(threadLocalLogs.get());
    }

    public static void clearLogs() {
        threadLocalLogs.get().clear();
    }

}