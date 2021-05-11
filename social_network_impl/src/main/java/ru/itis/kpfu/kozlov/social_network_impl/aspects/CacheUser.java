package ru.itis.kpfu.kozlov.social_network_impl.aspects;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CacheUser {
    String value() default "";
    String cacheName() default "";
    int timeToLiveInMinutes() default 1;
}
