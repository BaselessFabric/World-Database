package org.example.worlddb.model.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddb.model.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Hidden
public interface CountryEntityRepository extends JpaRepository<CountryEntity, String> {

}