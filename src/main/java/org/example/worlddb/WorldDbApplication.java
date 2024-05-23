package org.example.worlddb;

import org.example.worlddb.model.repositories.CountryLanguageEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;


@SpringBootApplication
public class WorldDbApplication {

    private static final Logger logger = Logger.getLogger("Spring Logger");

    public static void main(String[] args) {
        SpringApplication.run(WorldDbApplication.class, args);
    }

}
