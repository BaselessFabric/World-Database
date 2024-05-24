package org.example.worlddb.model.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddb.model.entities.CountryLanguageEntity;
import org.example.worlddb.model.entities.CountryLanguageEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Hidden
public interface CountryLanguageEntityRepository extends JpaRepository<CountryLanguageEntity, CountryLanguageEntityId> {

    Optional<CountryLanguageEntity> findCountryLanguageEntityById_LanguageAndId_CountryCode(String language, String countryCode);
}