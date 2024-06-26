package org.example.worlddb.service;

import org.example.worlddb.model.entities.CountryLanguageEntity;
import org.example.worlddb.model.entities.CountryLanguageEntityId;
import org.example.worlddb.model.repositories.CountryLanguageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryLanguageService {

    private final CountryLanguageEntityRepository countrylanguageEntityRepository;

    @Autowired
    public CountryLanguageService(CountryLanguageEntityRepository countrylanguageEntityRepository) {
        this.countrylanguageEntityRepository = countrylanguageEntityRepository;
    }

    public CountryLanguageEntity createCountryLanguage(CountryLanguageEntity countryLanguage) {
        return countrylanguageEntityRepository.save(countryLanguage);
    }

    public List<CountryLanguageEntity> getAllCountryLanguages() {
        return countrylanguageEntityRepository.findAll();
    }

    public Optional<CountryLanguageEntity> getCountryLanguageById(CountryLanguageEntityId id) {
        return countrylanguageEntityRepository.findById(id);
    }

    public Optional<CountryLanguageEntity> getCountryLanguageById(String countryCode, String language){
        return countrylanguageEntityRepository.findCountryLanguageEntityById_LanguageAndId_CountryCode(language, countryCode);
    }

    public CountryLanguageEntity updateCountryLanguage(CountryLanguageEntityId id, CountryLanguageEntity updatedCountryLanguage) {
        Optional<CountryLanguageEntity> countryLanguageOptional = countrylanguageEntityRepository.findById(id);
        if (countryLanguageOptional.isPresent()) {
            CountryLanguageEntity existingCountryLanguage = countryLanguageOptional.get();
            existingCountryLanguage.setCountryCode(updatedCountryLanguage.getCountryCode());
            existingCountryLanguage.setIsOfficial(updatedCountryLanguage.getIsOfficial());
            existingCountryLanguage.setPercentage(updatedCountryLanguage.getPercentage());

            return countrylanguageEntityRepository.save(existingCountryLanguage);
        } else {
            return null;
        }
    }

    public boolean deleteCountryLanguage(CountryLanguageEntityId id) {
        Optional<CountryLanguageEntity> countryLanguageOptional = countrylanguageEntityRepository.findById(id);
        if (countryLanguageOptional.isPresent()) {
            countrylanguageEntityRepository.delete(countryLanguageOptional.get());
            return true;
        } else {
            return false;
        }
    }

}