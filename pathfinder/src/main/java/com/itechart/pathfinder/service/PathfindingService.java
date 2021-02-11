package com.itechart.pathfinder.service;

import com.itechart.pathfinder.entity.City;
import com.itechart.pathfinder.exception.CityNotFoundException;
import com.itechart.pathfinder.exception.PathNotFoundException;
import com.itechart.pathfinder.model.Path;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PathfindingService {

    RouteService routeService;

    public List<Path> getPath(String fromCityName, String toCityName) {
        List<City> cities = routeService.initRouting();
        City fromCity = getByNameOrThrow(cities, fromCityName);
        City toCity = getByNameOrThrow(cities, toCityName);

        List<Path> paths = PathCalculator.calculateAllPaths(fromCity, toCity);
        if (paths.isEmpty()) {
            throw new PathNotFoundException(fromCityName, toCityName);
        }
        return paths;
    }

    private City getByNameOrThrow(List<City> cities, String name) throws CityNotFoundException {
        return cities.stream()
                .filter(city -> name.equals(city.getName()))
                .findFirst()
                .orElseThrow(() -> new CityNotFoundException(name));
    }

}
