package org.example.dungeonsanddebugerss;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.service.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

@SpringBootTest
class DungeonsAndDebugerssApplicationTests {

    @Autowired
    WorldService worldService;

    @Test
    void contextLoads() {
    }
  
    @Nested
    class testingFindCountOfMostPopularLanguage{
        @Test
        @DisplayName("Given Afghanistan, the number of people who speak the most popular official language is 11905280 ")
        void givenAfghanistanTheNumberOfPeopleWhoSpeakTheMostPopularOfficialLanguageIs11905280() {
            Assertions.assertEquals(11905280,worldService.findCountOfMostPopularLanguage("Afghanistan"));
        }

        @Test
        @DisplayName("Given a country doesn't exists, return negative 1")
        void givenACountryDoesnTExistsReturnNegative1() {
            Assertions.assertEquals(-1,worldService.findCountOfMostPopularLanguage("BABYLON"));
        }

        @Test
        @DisplayName("Given Aruba, the number of people who speak the most popular official language is ")
        void givenArubaTheNumberOfPeopleWhoSpeakTheMostPopularOfficialLanguageIs() {
            Assertions.assertEquals(5459,worldService.findCountOfMostPopularLanguage("Aruba"));
        }

        @Test
        @DisplayName("Given Angola, country has no official language so it should return -1")
        void givenAngolaCountryHasNoOfficialLanguageSoItShouldReturn1() {
            Assertions.assertEquals(-1,worldService.findCountOfMostPopularLanguage("Angola"));
        }


    }

    @Test
    void checkCountryWithNoHeadOfStateIsCalledSanMarino() {
        Assertions.assertEquals("San Marino", worldService.findCountriesWithNoHeadOfState().getFirst().getName());
    }
    @Transactional
    @Test
    @DisplayName("Testing that 5 lowest cities are returned")
    void checkFivesCitiesWithLowestPopulationAreReturned(){
        List<CityEntity> result = worldService.find5SmallestDistrictsOfCity("Zuid-Holland");

        assertEquals(5, result.size());
        assertEquals("Delft", result.get(0).getName());
        assertEquals("Zoetermeer", result.get(1).getName());
        assertEquals("Leiden", result.get(2).getName());
        assertEquals("Dordrecht", result.get(3).getName());
        assertEquals("Haag", result.get(4).getName());

    }
  
    @Test
    @DisplayName("Given invalid country, get percentage returns 0.")
    void givenInvalidCountryGetPercentageReturns0() {
        float expected = 0;
        float result = worldService.findPercentageOfPopulationInLargestCity("Not A Country");
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Given empty country, get percentage returns 0.")
    void givenEmptyCountryGetPercentReturns0() {
        float expected = 0;
        float result = worldService.findPercentageOfPopulationInLargestCity("");
        Assertions.assertEquals(expected, result);
    }

    @Test
    @DisplayName("Given valid country, return the percentage of the population of the highest city pop.")
    void givenValidCountryReturnThePercentageOfThePopulation() {
        float expected = 4.609178f;
        float result = worldService.findPercentageOfPopulationInLargestCity("Netherlands");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void checkCountryWithNoHeadOfStateIsNotMoreThanOne() {
        Assertions.assertEquals(1, worldService.findCountriesWithNoHeadOfState().size());
    }
    @Nested
    class findCountryWithMostCitiesTests {
        @Test
        void checkCountryWithMostCitiesReturnsChina() {
            Assertions.assertEquals("China", worldService.findCountryWithMostCity().getFirst().getName());
        }


        @Test
        void checkCountryWithMostCitiesListsIsNotMoreThanOne() {
            Assertions.assertEquals(1, worldService.findCountryWithMostCity().size());


        }
        @Test
        void checkNumOfMostCitiesReturns363(){
            Assertions.assertEquals(363, worldService.returnNumOfCities());
        }
    }
}
