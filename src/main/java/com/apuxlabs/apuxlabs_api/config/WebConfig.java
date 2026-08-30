package com.apuxlabs.apuxlabs_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Apply this to all your API endpoints
                .allowedOrigins("http://localhost:3000") // Allow your Next.js frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
                .allowedHeaders("*") // Allow all headers (necessary for multipart/form-data boundary generation)
                .allowCredentials(true); // Allow sending cookies/auth headers if needed in the future
    }
}