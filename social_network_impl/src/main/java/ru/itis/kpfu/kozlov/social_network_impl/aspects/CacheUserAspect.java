package ru.itis.kpfu.kozlov.social_network_impl.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import ru.itis.kpfu.kozlov.social_network_api.dto.UserDto;
import ru.itis.kpfu.kozlov.social_network_impl.services.CacheService;


@Component
@Aspect
public class CacheUserAspect {

    @Autowired
    private CacheService cacheService;

    @Around("@annotation(CacheUser)")
    public Object cacheUser(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object args[] = proceedingJoinPoint.getArgs();
        String email = (String) args[0];
        if(cacheService.containsUser(email)){
            return cacheService.getUser(email);
        }
        else{
            Object result = proceedingJoinPoint.proceed(args);
            cacheService.putUser(email, result);
            return result;
        }
    }

    @Around("@annotation(UpdateCache)")
    public Object update(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        UserDto updated = (UserDto)proceedingJoinPoint.proceed();
        cacheService.putUser(updated.getEmail(), updated);
        return updated;
    }
}
