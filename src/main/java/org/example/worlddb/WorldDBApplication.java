package org.example.worlddb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;


@SpringBootApplication
public class WorldDBApplication {

    private static final Logger logger = Logger.getLogger("Spring Logger");

    public static void main(String[] args) {
        SpringApplication.run(WorldDBApplication.class, args);
    }
}
