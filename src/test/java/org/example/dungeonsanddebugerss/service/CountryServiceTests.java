package org.example.dungeonsanddebugerss.service;

import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.example.dungeonsanddebugerss.model.respositories.CountryEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CountryServiceTests {

    @Autowired
    private CountryService countryService;

    @MockBean
    private CountryEntityRepository countryEntityRepository;

    @Test
    public void testCreateCountry() {
        CountryEntity country = new CountryEntity();
        country.setCode("USA");
        country.setName("United States");
        country.setContinent("North America");
        country.setRegion("North America");
        country.setSurfaceArea(BigDecimal.valueOf(9372610));
        country.setPopulation(331449281);


        when(countryEntityRepository.save(country)).thenReturn(country);

        CountryEntity savedCountry = countryService.createCountry(country);

        assertNotNull(savedCountry);
        assertEquals("USA", savedCountry.getCode());
        assertEquals("United States", savedCountry.getName());
        assertEquals("North America", savedCountry.getContinent());
    }

    @Test
    public void testGetAllCountries() {
        CountryEntity country1 = new CountryEntity();
        country1.setCode("USA");

        CountryEntity country2 = new CountryEntity();
        country2.setCode("CAN");

        when(countryEntityRepository.findAll()).thenReturn(Arrays.asList(country1, country2));

        List<CountryEntity> countries = countryService.getAllCountries();

        assertNotNull(countries);
        assertEquals("USA", countries.get(0).getCode());
        assertEquals("CAN", countries.get(1).getCode());
    }

    @Test
    public void testGetCountryByCode() {
        CountryEntity country = new CountryEntity();
        country.setCode("USA");

        when(countryEntityRepository.findById("USA")).thenReturn(Optional.of(country));

        Optional<CountryEntity> optionalCountry = countryService.getCountryByCode("USA");

        assertTrue(optionalCountry.isPresent());
        assertEquals("USA", optionalCountry.get().getCode());
    }

    @Test
    public void testUpdateCountry() {
        CountryEntity existingCountry = new CountryEntity();
        existingCountry.setCode("USA");
        existingCountry.setName("United States");

        CountryEntity updatedCountry = new CountryEntity();
        updatedCountry.setName("USA");

        when(countryEntityRepository.findById("USA")).thenReturn(Optional.of(existingCountry));
        when(countryEntityRepository.save(existingCountry)).thenReturn(existingCountry);

        CountryEntity result = countryService.updateCountry("USA", updatedCountry);

        assertNotNull(result);
        assertEquals("USA", result.getName());
    }

    @Test
    public void testDeleteCountry() {
        CountryEntity country = new CountryEntity();
        country.setCode("USA");

        when(countryEntityRepository.findById("USA")).thenReturn(Optional.of(country));
        doNothing().when(countryEntityRepository).delete(country);

        boolean result = countryService.deleteCountry("USA");

        assertTrue(result);
        verify(countryEntityRepository, times(1)).delete(country);
    }
}
