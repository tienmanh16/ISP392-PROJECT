package com.isp.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.isp.project.model.Email;

@Configuration
public class WebConfig {

    @Bean
    Email email() {
        return new Email();
    }
  
}
