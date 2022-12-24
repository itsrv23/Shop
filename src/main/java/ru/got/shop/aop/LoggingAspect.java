package ru.got.shop.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * *(..)) && within(ru.got.shop.service..*)")
    public void adsControllerPointcut() {
    }

    @Around("adsControllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Method name = {}() :: input  params = {}",
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            log.debug("Method output data::{}() with result = {}", joinPoint.getSignature().getName(), result);
            return result;
        } catch (RuntimeException e) {
            log.error("Illegal argument: {} in {}.{}()",
                    Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw e;
        }
    }
}
