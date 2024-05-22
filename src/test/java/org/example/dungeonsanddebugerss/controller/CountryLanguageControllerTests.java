package org.example.dungeonsanddebugerss.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
public class CountryLanguageControllerTests {
    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    @Test
    @DisplayName("Check that the status code is 200 when you get a language")
    void checkThatTheStatusCodeIs200WhenYouGetALanguage() {
        webTestClient.get()
                .uri("/api/language/countryCode?countryCode=ABW").
                exchange().
                expectStatus().
                is2xxSuccessful();
    }

    @Test
    @DisplayName("Check that the status code is 200 when you get all language")
    void checkThatTheStatusCodeIs200WhenYouGetAllLanguage() {
        webTestClient.get()
                .uri("/api/languages")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("Check that error code 4XX is provided when you get a language with an invalid country code")
    void checkThatErrorCode4xxIsProvidedWhenYouGetALanguageWithAnInvalidCountryCode() {
        webTestClient.get()
                .uri("/api/language/countryCode?countryCode=ZZZZZZZ")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }
}
