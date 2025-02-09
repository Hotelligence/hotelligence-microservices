package com.hotelligence.hotelservice.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableCaching
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient loadBalancedWebClientBuilder() {
        return WebClient.builder().build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all endpoints
                        .allowedOrigins("*") // Allow requests from all origins
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD") // Allow specific HTTP methods
                        .allowedHeaders("Access-Control-Allow-Origin") // Allow all headers
                        .allowCredentials(false); // Disable credentials (cookies, etc.)
            }
        };
    }
}
