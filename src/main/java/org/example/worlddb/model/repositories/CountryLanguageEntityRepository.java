package org.example.worlddb.model.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddb.model.entities.CountryLanguageEntity;
import org.example.worlddb.model.entities.CountryLanguageEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface CountryLanguageEntityRepository extends JpaRepository<CountryLanguageEntity, CountryLanguageEntityId> {
}