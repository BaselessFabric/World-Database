package org.example.dungeonsanddebugerss.controller;

import org.example.dungeonsanddebugerss.controllers.CityController;
import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class CityControllerTests {

    WebTestClient webTestClient;

    @Autowired
    private CityController cityController;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Given a post request with a valid city in its body, a 201 status code is returned")
    void givenAPostRequestWithAValidCityInItsBodyA201StatusCodeIsReturned() {
        CityEntity city = getCity("AND", 0);
        webTestClient.post()
                .uri("http://localhost:8080/api/city" + "?key=hi")
                .body(BodyInserters.fromValue(city))
                .exchange()
                .expectStatus()
                .isCreated();
    }

    private CityEntity getCity(String countryCode, int cityId) {
        CountryEntity country = new CountryEntity();
        country.setCode(countryCode);
        CityEntity city = new CityEntity();
        city.setId(cityId);
        city.setName("Test");
        city.setCountryCode(country);
        city.setDistrict("Test");
        city.setPopulation(0);
        return city;
    }

    @Test
    @DisplayName("Given a post request with an invalid country code, a 400 status code is returned")
    void givenAPostRequestWithAnInvalidCountryCodeA400StatusCodeIsReturned() {
        CityEntity city = getCity("ZZZ", 0);
        webTestClient.post()
                .uri("http://localhost:8080/api/city" + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectStatus()
                     .isBadRequest();
    }

    @Test
    @DisplayName("Given a post request with an invalid country code, the response body is in the expected format")
    void givenAPostRequestWithAnInvalidCountryCodeTheResponseBodyIsInTheExpectedFormat() {
        CityEntity city = getCity("ZZZ", 0);
        String expectedResponseBody = """
                {
                  "message": "Cannot find country with code: ZZZ",
                  "statusCode": 400,
                  "url": "http://localhost:8080/api/city"
                }
                """;
        webTestClient.post()
                     .uri("http://localhost:8080/api/city" + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectBody()
                .json(expectedResponseBody);
    }

    @Test
    @DisplayName("Given a post request with a city ID that already exists, a 400 status code is returned")
    void givenAPostRequestWithACityIdThatAlreadyExistsA400StatusCodeIsReturned() {
        CityEntity city = getCity("AND", 200);
        webTestClient.post()
                     .uri("http://localhost:8080/api/city" + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectStatus()
                     .isBadRequest();
    }

    @Test
    @DisplayName("Given a request with a city ID that already exists, the response body is in the expected format")
    void givenARequestWithACityIdThatAlreadyExistsTheResponseBodyIsInTheExpectedFormat() {
        CityEntity city = getCity("AND", 200);
        String expectedResponseBody = """
                {
                  "message": "City with ID 200 already exists.",
                  "statusCode": 400,
                  "url": "http://localhost:8080/api/city"
                }
                """;
        webTestClient.post()
                     .uri("http://localhost:8080/api/city" + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectBody()
                .json(expectedResponseBody);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Given a put request with a valid city that is already present, a 200 status code response is returned")
    void givenAPutRequestWithAValidCityThatIsAlreadyPresentA200StatusCodeResponseIsReturned() {
        int cityId = 200;
        CityEntity city = getCity("AND", cityId);
        webTestClient.put()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful();
    }

    @Test
    @DisplayName("Given a successful put request, the response body contains a city with the correct ID")
    void givenASuccessfulPutRequestTheResponseBodyContainsACityWithTheCorrectId() {
        int cityId = 200;
        CityEntity city = getCity("AND", cityId);
        webTestClient.put()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectBody(CityEntity.class)
                .value(responseCity -> Assertions.assertEquals(cityId, responseCity.getId()));
    }

    @Test
    @DisplayName("Given a put request that references a non-existant country, a 400 status code is returned")
    void givenAPutRequestThatReferencesANonExistantCountryA400StatusCodeIsReturned() {
        int cityId = 200;
        CityEntity city = getCity("ZZZ", cityId);
        webTestClient.put()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectStatus()
                .isBadRequest();
    }

    @Test
    @DisplayName("Given a put request that references a country that does not exist, the correct response body is returned")
    void givenAPutRequestThatReferencesACountryThatDoesNotExistTheCorrectResponseBodyIsReturned() {
        int cityId = 200;
        CityEntity city = getCity("ZZZ", cityId);
        String expectedResponseBody = """
                {
                  "message": "Cannot find country with code: ZZZ",
                  "statusCode": 400,
                  "url": "http://localhost:8080/api/city/%d"
                }
                """.formatted(cityId);
        webTestClient.put()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectBody()
                .json(expectedResponseBody);
    }

    @Test
    @DisplayName("Given a put request with a city id that does not exist, a 404 response code is returned")
    void givenAPutRequestWithACityIdThatDoesNotExistA404ResponseCodeIsReturned() {
        int cityId = 9999;
        CityEntity city = getCity("AND", cityId);
        webTestClient.put()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectStatus()
                     .isBadRequest();
    }

    @Test
    @DisplayName("Given a put request with a city id that does not exist, the expected response body is returned")
    void givenAPutRequestWithACityIdThatDoesNotExistTheExpectedResponseBodyIsReturned() {
        int cityId = 9999;
        CityEntity city = getCity("AND", cityId);
        String expectedResponseBody = """
                {
                  "message": "Could not find city: Test",
                  "statusCode": 400,
                  "url": "/api/city/%d"
                }
                """.formatted(cityId);
        webTestClient.put()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .body(BodyInserters.fromValue(city))
                     .exchange()
                     .expectBody()
                     .json(expectedResponseBody);
    }

    // This code only succeeds the first time it is run, thereafter the city is deleted so the tests fail.
//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("Given a delete request for a city id that is in the database, return a 200 status code")
//    void givenADeleteRequestForACityIdThatIsInTheDatabaseReturnA200StatusCode() {
//        int cityId = 100;
//        webTestClient.delete()
//                     .uri("http://localhost:8080/api/city/" + cityId)
//                     .exchange()
//                     .expectStatus()
//                     .is2xxSuccessful();
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    @DisplayName("Given a delete request for a city id that is in the database, the response body contains a city with that id")
//    void givenADeleteRequestForACityIdThatIsInTheDatabaseTheResponseBodyContainsACityWithThatId() {
//        int cityId = 100;
//        webTestClient.delete()
//                     .uri("http://localhost:8080/api/city/" + cityId)
//                     .exchange()
//                     .expectBody(CityEntity.class)
//                .value(city -> Assertions.assertEquals(cityId, city.getId()));
//    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Given a delete request for a city id that does not exist, returns a 400 response code")
    void givenADeleteRequestForACityIdThatDoesNotExistReturnsA400ResponseCode() {
        int cityId = 9999;
        webTestClient.delete()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .exchange()
                     .expectStatus()
                     .isBadRequest();
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("Given a delete request for city id that does not exist, returns the expected response body")
    void givenADeleteRequestForCityIdThatDoesNotExistReturnsTheExpectedResponseBody() {
        int cityId = 9999;
        String expectedResponseBody = """
                {
                  "message": "Could not find city: %d",
                  "statusCode": 400,
                  "url": "/api/city/%d"
                }
                """.formatted(cityId, cityId);
        webTestClient.delete()
                     .uri("http://localhost:8080/api/city/" + cityId + "?key=hi")
                     .exchange()
                     .expectBody()
                .json(expectedResponseBody);

    }

    @Test
    @DisplayName("Check that the status code is 200 when getting city by ID")
    void checkThatTheStatusCodeIs200WhenGettingCityById() {
        webTestClient.get()
                .uri("http://localhost:8080/api/city/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }
    @Test
    @DisplayName("Check that the status code is 200 when getting city by name")
    void checkThatTheStatusCodeIs200WhenGettingCityByName() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/name?name=London")
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful();
    }
    @Test
    @DisplayName("Check that the status code is 200 when getting all cities")
    void checkThatTheStatusCodeIs200WhenGettingAllCities() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/cities")
                     .exchange()
                     .expectStatus()
                     .is2xxSuccessful();
    }
    @Test
    @DisplayName("Check that the status code is 400 when getting city id does not exist")
    void checkThatTheStatusCodeIs400WhenIdDoesNotExist() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/909090909")
                     .exchange()
                     .expectStatus()
                     .is4xxClientError();
    }
    @Test
    @DisplayName("Check that the status code is 400 when city name does not exist")
    void checkThatTheStatusCodeIs200WhenNameDoesNotExist() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/abc")
                     .exchange()
                     .expectStatus()
                     .is4xxClientError();
    }
    @Test
    @DisplayName("Check that a city gets returned when providing its name")
    void checkThatCityisReturnedByProvidingName() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/name?name=Kabul")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody()
                     .json("[\n" +
                                   "    {\n" +
                                   "        \"id\": 1,\n" +
                                   "        \"name\": \"Kabul\",\n" +
                                   "        \"countryCode\": {\n" +
                                   "            \"code\": \"AFG\",\n" +
                                   "            \"name\": \"Afghanistan\",\n" +
                                   "            \"continent\": \"Asia\",\n" +
                                   "            \"region\": \"Southern and Central Asia\",\n" +
                                   "            \"surfaceArea\": 652090.00,\n" +
                                   "            \"indepYear\": 1919,\n" +
                                   "            \"population\": 22720000,\n" +
                                   "            \"lifeExpectancy\": 45.9,\n" +
                                   "            \"gnp\": 5976.00,\n" +
                                   "            \"localName\": \"Afganistan/Afqanestan\",\n" +
                                   "            \"governmentForm\": \"Islamic Emirate\",\n" +
                                   "            \"headOfState\": \"Mohammad Omar\",\n" +
                                   "            \"capital\": 1,\n" +
                                   "            \"code2\": \"AF\",\n" +
                                   "            \"gnpold\": null\n" +
                                   "        },\n" +
                                   "        \"district\": \"Kabol\",\n" +
                                   "        \"population\": 1780000\n" +
                                   "    }\n" +
                                   "]");
    }

    @Test
    @DisplayName("Check that correct json body returns when wrong name is provided")
    void checkCorrectJsonBodyisReturnedWhenIncorrectNameIsProvided() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/name=abc")
                     .exchange()
                     .expectStatus().isBadRequest()
                     .expectBody()
                     .json("{\n" +
                                   "    \"error\": \"Bad Request\",\n" +
                                   "    \"path\": \"/api/city/name=abc\"\n" +
                                   "}");
    }



    @Test
    @DisplayName("Check that a city gets returned when providing the correct id")
    void checkThatCityisReturnedByProvidingCorrectId() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/1")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBody()
                     .json("{\n" +
                                   "    \"id\": 1,\n" +
                                   "    \"name\": \"Kabul\",\n" +
                                   "    \"countryCode\": {\n" +
                                   "        \"code\": \"AFG\",\n" +
                                   "        \"name\": \"Afghanistan\",\n" +
                                   "        \"continent\": \"Asia\",\n" +
                                   "        \"region\": \"Southern and Central Asia\",\n" +
                                   "        \"surfaceArea\": 652090.00,\n" +
                                   "        \"indepYear\": 1919,\n" +
                                   "        \"population\": 22720000,\n" +
                                   "        \"lifeExpectancy\": 45.9,\n" +
                                   "        \"gnp\": 5976.00,\n" +
                                   "        \"localName\": \"Afganistan/Afqanestan\",\n" +
                                   "        \"governmentForm\": \"Islamic Emirate\",\n" +
                                   "        \"headOfState\": \"Mohammad Omar\",\n" +
                                   "        \"capital\": 1,\n" +
                                   "        \"code2\": \"AF\",\n" +
                                   "        \"gnpold\": null\n" +
                                   "    },\n" +
                                   "    \"district\": \"Kabol\",\n" +
                                   "    \"population\": 1780000\n" +
                                   "}");
    }
    @Test
    @DisplayName("Check that a city  does not get returned when providing a wrong id")
    void checkThatWrongIdShowsCorrectJson() {
        webTestClient.get()
                     .uri("http://localhost:8080/api/city/9999")
                     .exchange()
//                     .expectStatus().isBadRequest()
                     .expectBody()
                     .json("{\n" +
                                   "    \"message\": \"Could not find city: 9999\",\n" +
                                   "    \"statusCode\": 400,\n" +
                                   "    \"url\": \"/api/city/9999\"\n" +
                                   "}");
    }

}
