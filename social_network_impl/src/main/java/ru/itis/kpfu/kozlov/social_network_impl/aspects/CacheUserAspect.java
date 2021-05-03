package ru.itis.kpfu.kozlov.social_network_impl.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CacheUserAspect {

    private final Map<String, Object> cachedUsers = new HashMap<>();

    @Around("@annotation(CacheUser)")
    public Object cacheUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        System.out.println("Aspect");
        String email = (String) args[0];
        if (cachedUsers.containsKey(email)) {
            return cachedUsers.get(email);
        } else {
            Object result = proceedingJoinPoint.proceed();
            cachedUsers.put(email, result);
            return result;
        }
    }

}
