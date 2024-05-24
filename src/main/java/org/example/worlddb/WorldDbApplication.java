package org.example.worlddb;

import org.example.worlddb.model.repositories.CountryLanguageEntityRepository;
import org.example.worlddb.service.CountryLanguageService;
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

    @Bean
    CommandLineRunner runner(CountryLanguageService countryLanguageService) {
        return args -> {
          System.out.println("Country Language: " + countryLanguageService.getCountryLanguageById("ABW", "English").get());
          System.out.println("\n");
        };
    }

}
