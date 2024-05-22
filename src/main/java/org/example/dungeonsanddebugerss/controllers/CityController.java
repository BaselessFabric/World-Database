package org.example.dungeonsanddebugerss.controllers;

import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.example.dungeonsanddebugerss.model.exception.CityAlreadyExistsException;
import org.example.dungeonsanddebugerss.model.exception.CityDoesNotExistException;
import org.example.dungeonsanddebugerss.model.exception.CountryDoesNotExistException;
import org.example.dungeonsanddebugerss.model.exception.KeyNotFoundException;
import org.example.dungeonsanddebugerss.service.CityService;
import org.example.dungeonsanddebugerss.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CityController {
    private final CityService cityService;
    private final CountryService countryService;

    @Autowired
    public CityController(CityService cityService, CountryService countryService) {
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @PostMapping("/city")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCity(@RequestBody CityEntity city, @RequestParam(name="key", required = false) String key) throws CountryDoesNotExistException, CityAlreadyExistsException, KeyNotFoundException {
        checkForKey(key);
        cityService.checkCityDoesNotExist(city);
        fetchFullCountryDetails(city);
        cityService.createCity(city);
    }

    private void fetchFullCountryDetails(CityEntity city) throws CountryDoesNotExistException {
        CountryEntity country = getCountryFromCity(city);
        city.setCountryCode(country);
    }

    private CountryEntity getCountryFromCity(CityEntity city) throws CountryDoesNotExistException {
        return countryService.getCountryByCode(city.getCountryCode().getCode())
                             .orElseThrow(() -> new CountryDoesNotExistException(city.getCountryCode().getCode()));
    }

    @GetMapping("/cities")
    public List<CityEntity> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/city/{id}")
    public CityEntity getCityById(@PathVariable int id) throws CityDoesNotExistException {
        Optional<CityEntity> cityOptional = cityService.getCityById(id);
        if(cityOptional.isEmpty()){
            throw new CityDoesNotExistException(Integer.toString(id));
        }
        return cityService.getCityById(id).get();
    }

    @GetMapping("/city/name")
    public List<CityEntity> getCityProperty(@RequestParam(name="name") String name) throws CityDoesNotExistException {
        List<CityEntity> city = cityService.findCitiesByName(name);
        if(city.isEmpty()){
            throw new CityDoesNotExistException(name);
        } else{
            return city;
        }
    }

    @PutMapping("/city/{id}")
    public CityEntity updateCity(@RequestBody CityEntity city, @PathVariable Integer id, @RequestParam(name="key", required = false) String key) throws CityDoesNotExistException, CountryDoesNotExistException, KeyNotFoundException {
        checkForKey(key);
        fetchFullCountryDetails(city);
        CityEntity updatedCity = cityService.updateCity(id, city);
        if (updatedCity == null) {
            throw new CityDoesNotExistException(city.getName());
        }
        return updatedCity;
    }

    @DeleteMapping("/city/{id}")
    public CityEntity deleteCity(@PathVariable int id, @RequestParam(name="key", required = false) String key) throws CityDoesNotExistException, KeyNotFoundException {
        checkForKey(key);
        CityEntity city = cityService.getCityById(id)
                .orElseThrow(() -> new CityDoesNotExistException(String.valueOf(id)));
        cityService.deleteCity(id);
        return city;
    }

    private void checkForKey(String key) throws KeyNotFoundException {
        if (key == null || key.isEmpty()) {
            throw new KeyNotFoundException();
        }
    }
}
