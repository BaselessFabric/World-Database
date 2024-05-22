package org.example.worlddb.model.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddb.model.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Hidden
public interface CountryEntityRepository extends JpaRepository<CountryEntity, String> {
//    List<CountryEntity> findAllCountryEntity_OrderByRandom();
    List<CountryEntity> findAllByOrderByNameRandom();
}