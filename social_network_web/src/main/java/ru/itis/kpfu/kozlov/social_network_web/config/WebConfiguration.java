package ru.itis.kpfu.kozlov.social_network_web.config;



import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import ru.itis.kpfu.kozlov.social_network_impl.config.ImplementationGlobalConfig;
import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;

@EnableWebMvc
@Configuration

@Import({ImplementationGlobalConfig.class})
public class WebConfiguration {

    @Bean
    MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.parse("128KB"));
        factory.setMaxRequestSize(DataSize.parse("128KB"));
        return factory.createMultipartConfig();
    }

    /*@Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }*/
}
