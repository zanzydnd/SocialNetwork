package ru.itis.kpfu.kozlov.social_network_impl.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Aspect
public class LogAspect {
    private static final Logger logger = Logger.getLogger(LogAspect.class.getName());

    @Around("@annotation(ExecutionTime)")
    public Object methodTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = proceedingJoinPoint.proceed();
        long end = (System.currentTimeMillis() - start);
        logger.log(Level.INFO, proceedingJoinPoint.getSignature() + "executed: " + end + "milli sec");
        return proceed;
    }
}
