package org.example.dungeonsanddebugerss.controller;

import org.example.dungeonsanddebugerss.controllers.CountryController;
import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableWebMvc
public class CountryControllerTests {
    private WebTestClient webTestClient;
    @Autowired
    private CountryController countryController;

    @BeforeEach
    public void setup(){
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    @Test
    @DisplayName("Check endpoint /countries returns status code 200")
    @Transactional
    void checkGetCountriesReturns200(){
        webTestClient.get().uri("/api/countries")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
   }
    @Test
    @DisplayName("Check that the first country is Afghanistan")
    @Transactional
    void checkThatFirstCountryIsAfgahanistan(){
        webTestClient.get()
                .uri("http://localhost:8080/api/country/AFG")
                .exchange()
                .expectBody(CountryEntity.class)
                .value(country -> assertEquals("Afghanistan", country.getName()));
    }

    @Test
    @DisplayName("Given a post request with a valid city in its body, a 201 status code is returned")
    @Transactional
    void givenAPostRequestWithAValidCountryA201StatusCodeIsReturned() {
        CountryEntity country = getCountry("CHN");
        webTestClient.post()
                .uri("http://localhost:8080/api/country"  + "?key=hi")
                .body(BodyInserters.fromValue(country))
                .exchange()
                .expectStatus()
                .isCreated();
    }

    private CountryEntity getCountry(String countryCode) {
        CountryEntity country = new CountryEntity();
        country.setName("China");
        country.setCode(countryCode);
        country.setSurfaceArea(BigDecimal.valueOf(193.00));
        country.setPopulation(0);
        country.setGovernmentForm("test");
        country.setLocalName("test");
        country.setCapital(0);
        country.setCode2("ON");
        country.setContinent("Asia");
        country.setRegion("Dunno");
        country.setGNPOld(BigDecimal.valueOf(793.00));
        return country;
    }
    @Test
    @DisplayName("Give a country with invalid code check that it returns 404")
    void givenACountryWithInvalidCodeCheckThatItReturns404() {
        webTestClient.get()
                .uri("/api/country/666")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}
