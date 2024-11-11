package com.pablo.pokedex.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures CORS for the application to allow specific origins and HTTP methods.
 * <p>
 * This configuration allows the frontend to interact with the backend API
 * by enabling cross-origin requests from localhost:3000 (React development server).
 */
@Configuration
@Api(value = "CORS Configuration", description = "Configures CORS settings for the application to allow specific origins and HTTP methods.")
public class CorsConfig {

    /**
     * Configures the CORS mappings for the API.
     * <p>
     * This allows the frontend running on localhost:3000 to make requests
     * to the backend on localhost:8080.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // React frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
