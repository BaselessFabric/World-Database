package org.example.dungeonsanddebugerss.controllers;

import org.example.dungeonsanddebugerss.model.entities.CountryLanguageEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryLanguageEntityId;
import org.example.dungeonsanddebugerss.model.exception.CountryLanguageNotFoundException;
import org.example.dungeonsanddebugerss.model.exception.CountryNotFoundException;
import org.example.dungeonsanddebugerss.model.exception.KeyNotFoundException;
import org.example.dungeonsanddebugerss.model.exception.LanguageAlreadyExistsForCountryException;
import org.example.dungeonsanddebugerss.service.CountryLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CountryLanguageController {
    private final CountryLanguageService countryLanguageService;

    @Autowired
    public CountryLanguageController(CountryLanguageService countryLanguageService) {
        this.countryLanguageService = countryLanguageService;
    }

    @PostMapping("/language")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCountryLanguage(@RequestBody CountryLanguageEntity countryLanguage, @RequestParam(name="key", required = false) String key) throws LanguageAlreadyExistsForCountryException, KeyNotFoundException {
        checkForKey(key);
        Optional<CountryLanguageEntity> language = countryLanguageService.getCountryLanguageById(countryLanguage.getId());
        if(language.isEmpty()){
            countryLanguageService.createCountryLanguage(countryLanguage);
        }else{
            throw new LanguageAlreadyExistsForCountryException("Country language already exists");
        }
    }

    @GetMapping("/languages")
    public List<CountryLanguageEntity> getAllCountryLanguages(){
        return countryLanguageService.getAllCountryLanguages();
    }


    @GetMapping("/language/countryCode")
    public List<CountryLanguageEntity> getCountryLanguageByCountryCode(@RequestParam("countryCode") String countryCode) throws CountryNotFoundException {
        List<CountryLanguageEntity> languages = countryLanguageService.getAllCountryLanguages().stream()
                .filter(c -> c.getCountryCode().getCode()
                .contains(countryCode)).toList();
        if(languages.isEmpty()){
            throw new CountryNotFoundException(countryCode);
        }else{
            return languages;
        }
    }

    @PutMapping("/language/countryCode/lang")
    public CountryLanguageEntity updateCountryLanguage(@RequestParam("countryCode") String countryCode,
                                                       @RequestParam("lang") String language,
                                                       @RequestBody CountryLanguageEntity countryLanguage,
                                                       @RequestParam(name="key", required = false) String key) throws CountryLanguageNotFoundException, KeyNotFoundException {

        checkForKey(key);
        Optional<CountryLanguageEntity> languageEntity = getCountryLanguageEntity(countryCode,language);
        if(languageEntity.isEmpty()){
            throw new CountryLanguageNotFoundException("Can not update because country code and language can't be found" +
                    "\nCountryCode: " + countryCode +
                    "\nLanguage: " + language
            );
        }else{
            return countryLanguageService.updateCountryLanguage(languageEntity.get().getId(), countryLanguage);
        }
    }

    @DeleteMapping("/language")
    public String deleteCountryLanguage(@RequestParam("countryCode") String countryCode,
                                        @RequestParam("lang") String language,
                                        @RequestParam(name="key", required = false) String key) throws CountryLanguageNotFoundException, KeyNotFoundException {
        checkForKey(key);
        Optional<CountryLanguageEntity> languageEntityToDelete = getCountryLanguageEntity(countryCode, language);
        if(languageEntityToDelete.isEmpty()){
            throw new CountryLanguageNotFoundException("Can not delete country Language because Country can not be found" +
                    "\nCountryCode: " + countryCode +
                    "\nLanguage: " + language );
            }else{
                return "Language successfully deleted: " + countryLanguageService.deleteCountryLanguage(languageEntityToDelete.get().getId());
        }
    }

    private Optional<CountryLanguageEntity> getCountryLanguageEntity(String countryCode, String language){
        for(CountryLanguageEntity languageEntity :countryLanguageService.getAllCountryLanguages()){
            CountryLanguageEntityId languageEntityID = languageEntity.getId();
            if(languageEntityID.getLanguage().equals(language) && languageEntityID.getCountryCode().equals(countryCode)){
                return Optional.of(languageEntity);
            }
        }
        return Optional.empty();
    }

    private void checkForKey(String key) throws KeyNotFoundException {
        if (key == null || key.isEmpty()) {
            throw new KeyNotFoundException();
        }
    }
}
