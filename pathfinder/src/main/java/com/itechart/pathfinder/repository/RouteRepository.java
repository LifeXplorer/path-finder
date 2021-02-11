package com.itechart.pathfinder.repository;

import com.itechart.pathfinder.entity.City;
import com.itechart.pathfinder.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    boolean existsByFromCityAndToCity(City fromCity, City toCity);

    Optional<Route> getByFromCityAndToCity(City fromCity, City toCity);

    default boolean isNewDistance(City fromCity, City toCity) {
        return !existsByFromCityAndToCity(fromCity, toCity);
    }

}
