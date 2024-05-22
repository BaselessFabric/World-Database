package org.example.dungeonsanddebugerss.controllers;

import jakarta.validation.Valid;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.example.dungeonsanddebugerss.model.exception.CountryCodeDoesNotExistException;
import org.example.dungeonsanddebugerss.model.exception.CountryIsNullException;
import org.example.dungeonsanddebugerss.model.exception.CountryNotFoundException;
import org.example.dungeonsanddebugerss.model.exception.KeyNotFoundException;
import org.example.dungeonsanddebugerss.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CountryController {
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/country")
    @ResponseStatus(HttpStatus.CREATED)
    public String addCountry(@Valid @RequestBody Optional<CountryEntity> countryEntity, @RequestParam(name="key", required = false) String key) throws CountryIsNullException, KeyNotFoundException {

        checkForKey(key);

        if (countryEntity.isEmpty()) {
            throw new CountryIsNullException();
        }else {

            countryService.createCountry(countryEntity.get());
            return "Country: " + countryEntity.get().getName() + " has been added to the database";
        }
    }

    @GetMapping("/countries")
    public List<CountryEntity> getAllCountries(){
        return countryService.getAllCountries();
    }

    @GetMapping("/country/{countryCode}")
    public CountryEntity getCountryById(@PathVariable String countryCode) throws CountryCodeDoesNotExistException {
         Optional<CountryEntity> findCountryCode = countryService.getCountryByCode(countryCode);
         if(findCountryCode.isPresent()){
             return findCountryCode.get();
         }

         throw new CountryCodeDoesNotExistException(countryCode);
    }

    @PutMapping("/country/{countryCode}")
    public Optional<CountryEntity> updateCountry(@Valid @RequestBody Optional<CountryEntity> country, @PathVariable String countryCode, @RequestParam(name="key", required = false) String key)
            throws CountryCodeDoesNotExistException, CountryIsNullException, KeyNotFoundException {

        checkForKey(key);

        Optional<CountryEntity> findCountry = countryService.getCountryByCode(countryCode);

        if (findCountry.isPresent() && country.isPresent()) {
            countryService.updateCountry(countryCode, country.get());
        }
        else if (findCountry.isEmpty()) {
            throw new CountryCodeDoesNotExistException(countryCode);
        }
        else if(!country.isPresent()){
            throw new CountryIsNullException();
        }

        return countryService.getCountryByCode(countryCode);
    }

    @DeleteMapping("/country/{countryCode}")
    public String deleteCountry(@PathVariable String countryCode, @RequestParam(name="key", required = false) String key) throws CountryCodeDoesNotExistException, KeyNotFoundException {
        checkForKey(key);

        Optional<CountryEntity> countryToRemove = countryService.getCountryByCode(countryCode);

        if (countryToRemove.isPresent()) {
            countryService.deleteCountry(countryCode);
            return  "Country with country code: " + countryCode + " deleted successfully";
        }

        throw new CountryCodeDoesNotExistException(countryCode);
    }

    private void checkForKey(String key) throws KeyNotFoundException {
        if (key == null || key.isEmpty()) {
            throw new KeyNotFoundException();
        }
    }


}

