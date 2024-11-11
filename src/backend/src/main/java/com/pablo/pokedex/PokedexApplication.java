package com.pablo.pokedex;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main entry point of the Pokedex application.
 * <p>
 * This class bootstraps the Spring Boot application and starts the embedded web server.
 */
@SpringBootApplication
@EnableCaching
@Api(value = "Pokedex Application", description = "Main entry point to start the Spring Boot application for the Pokedex project.")
public class PokedexApplication {
    public static void main(String[] args) {
        SpringApplication.run(PokedexApplication.class, args);
    }
}
