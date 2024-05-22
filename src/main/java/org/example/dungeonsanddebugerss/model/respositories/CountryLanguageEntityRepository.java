package org.example.dungeonsanddebugerss.model.respositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.dungeonsanddebugerss.model.entities.CountryLanguageEntity;
import org.example.dungeonsanddebugerss.model.entities.CountryLanguageEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface CountryLanguageEntityRepository extends JpaRepository<CountryLanguageEntity, CountryLanguageEntityId> {
}