package ru.itis.kpfu.kozlov.social_network_impl.config;


import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EntityScan(basePackages = "ru.itis.kpfu.kozlov.social_network_impl")
@EnableJpaRepositories(basePackages = "ru.itis.kpfu.kozlov.social_network_impl.jpa.repository")
@ComponentScan("ru.itis.kpfu.kozlov.social_network_impl.services")
@ComponentScan("ru.itis.kpfu.kozlov.social_network_impl.aspects")
public class ImplementationConfig {
    @Bean
    public ModelMapper ModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
