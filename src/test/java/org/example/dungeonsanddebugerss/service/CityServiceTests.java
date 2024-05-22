package org.example.dungeonsanddebugerss.service;

import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.example.dungeonsanddebugerss.model.respositories.CityEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CityServiceTests {

    @Autowired
    private CityService cityService;

    @MockBean
    private CityEntityRepository cityEntityRepository;

    @Test
    public void testCreateCity() {
        CityEntity city = new CityEntity();
        city.setName("Test City");
        city.setCountryCode(new CountryEntity());
        city.setDistrict("Test District");
        city.setPopulation(10000);

        when(cityEntityRepository.save(city)).thenReturn(city);

        CityEntity savedCity = cityService.createCity(city);

        assertNotNull(savedCity);
        assertEquals("Test City", savedCity.getName());
        assertEquals("Test District", savedCity.getDistrict());
        assertEquals(10000, savedCity.getPopulation());
    }


    @Test
    public void testGetAllCities() {
        CityEntity city1 = new CityEntity();
        city1.setId(1);
        city1.setName("City 1");

        CityEntity city2 = new CityEntity();
        city2.setId(2);
        city2.setName("City 2");

        when(cityEntityRepository.findAll()).thenReturn(Arrays.asList(city1, city2));

        List<CityEntity> cities = cityService.getAllCities();

        assertNotNull(cities);
        assertEquals(2, cities.size());
        assertEquals("City 1", cities.get(0).getName());
        assertEquals("City 2", cities.get(1).getName());
    }

    @Test
    public void testGetCityById() {
        CityEntity city = new CityEntity();
        city.setId(1);
        city.setName("Test City");

        when(cityEntityRepository.findById(1)).thenReturn(Optional.of(city));

        Optional<CityEntity> optionalCity = cityService.getCityById(1);

        assertTrue(optionalCity.isPresent());
        assertEquals("Test City", optionalCity.get().getName());
    }

    @Test
    public void testUpdateCity() {
        CityEntity existingCity = new CityEntity();
        existingCity.setId(1);
        existingCity.setName("Existing City");

        CityEntity updatedCity = new CityEntity();
        updatedCity.setName("Updated City");

        when(cityEntityRepository.findById(1)).thenReturn(Optional.of(existingCity));
        when(cityEntityRepository.save(existingCity)).thenReturn(existingCity);

        CityEntity result = cityService.updateCity(1, updatedCity);

        assertNotNull(result);
        assertEquals("Updated City", result.getName());
    }

    @Test
    public void testDeleteCity() {
        CityEntity city = new CityEntity();
        city.setId(1);

        when(cityEntityRepository.findById(1)).thenReturn(Optional.of(city));
        cityEntityRepository.delete(city);

        boolean result = cityService.deleteCity(1);

        assertTrue(result);
    }

}
