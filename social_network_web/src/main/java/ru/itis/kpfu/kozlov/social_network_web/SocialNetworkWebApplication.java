package ru.itis.kpfu.kozlov.social_network_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.itis.kpfu.kozlov.social_network_impl.SocialNetworkImplApplication;

import java.io.IOException;
import java.util.logging.LogManager;

@SpringBootApplication
public class SocialNetworkWebApplication {

    public static void main(String[] args) {
            try {
                LogManager.getLogManager().readConfiguration(
                        SocialNetworkImplApplication.class.getResourceAsStream("/logging.properties")
                );
            } catch (IOException e) {
                System.err.print("Could not setup logger configuration: " + e.toString());
            }
        SpringApplication.run(SocialNetworkWebApplication.class, args);
    }

}
