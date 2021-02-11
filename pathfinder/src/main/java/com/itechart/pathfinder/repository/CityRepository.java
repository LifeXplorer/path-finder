package com.itechart.pathfinder.repository;

import com.itechart.pathfinder.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    //TODO think of different cities with same names(Brest.Belarus and Brest.France)
//    boolean existsByName(String name);

    Optional<City> getByName(String name);

//    List<City> getByNameIn(List<String> names);
}
