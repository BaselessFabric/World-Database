package org.example.worlddb.model.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddb.model.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Hidden
public interface CityEntityRepository extends JpaRepository<CityEntity, Integer> {

    List<CityEntity> findByName(String name);
}