package org.example.dungeonsanddebugerss.model.respositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.dungeonsanddebugerss.model.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface CountryEntityRepository extends JpaRepository<CountryEntity, String> {
}