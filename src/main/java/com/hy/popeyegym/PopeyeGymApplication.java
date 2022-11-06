package com.hy.popeyegym;

import com.hy.popeyegym.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class PopeyeGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopeyeGymApplication.class, args);
    }

}
