package org.example.dungeonsanddebugerss.service;

import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.example.dungeonsanddebugerss.model.respositories.CountryEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private final CountryEntityRepository countryRepository;

    @Autowired
    public CountryService(CountryEntityRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public CountryEntity createCountry(CountryEntity country) {
        return countryRepository.save(country);
    }

    public List<CountryEntity> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<CountryEntity> getCountryByCode(String code) {
        if (code == null) return Optional.empty();
        return countryRepository.findById(code);
    }

    public CountryEntity updateCountry(String code, CountryEntity updatedCountry) {
        Optional<CountryEntity> countryOptional = countryRepository.findById(code);
        if (countryOptional.isPresent()) {
            CountryEntity existingCountry = countryOptional.get();
            existingCountry.setName(updatedCountry.getName());
            existingCountry.setContinent(updatedCountry.getContinent());
            existingCountry.setRegion(updatedCountry.getRegion());
            existingCountry.setSurfaceArea(updatedCountry.getSurfaceArea());
            existingCountry.setIndepYear(updatedCountry.getIndepYear());
            existingCountry.setPopulation(updatedCountry.getPopulation());
            existingCountry.setLifeExpectancy(updatedCountry.getLifeExpectancy());
            existingCountry.setGnp(updatedCountry.getGnp());
            existingCountry.setGNPOld(updatedCountry.getGNPOld());
            existingCountry.setLocalName(updatedCountry.getLocalName());
            existingCountry.setGovernmentForm(updatedCountry.getGovernmentForm());
            existingCountry.setHeadOfState(updatedCountry.getHeadOfState());
            existingCountry.setCapital(updatedCountry.getCapital());
            existingCountry.setCode2(updatedCountry.getCode2());

            return countryRepository.save(existingCountry);
        } else {
            return null;
        }
    }

    public boolean deleteCountry(String code) {
        Optional<CountryEntity> countryOptional = countryRepository.findById(code);
        if (countryOptional.isPresent()) {
            countryRepository.delete(countryOptional.get());
            return true;
        } else {
            return false;
        }
    }
}