package ru.itis.kpfu.kozlov.social_network_web.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.itis.kpfu.kozlov.social_network_impl.config.ImplementationConfig;

@EnableWebMvc
@Configuration
@Import({ImplementationConfig.class})
public class WebConfiguration {
}
