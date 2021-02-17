package com.itechart.pathfinder.service;

import com.itechart.pathfinder.dto.CityVertex;
import com.itechart.pathfinder.dto.Path;
import com.itechart.pathfinder.exception.CityNotFoundException;
import com.itechart.pathfinder.exception.PathNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PathfindingService {

    RouteService routeService;

    public List<Path> getPath(String fromCityName, String toCityName) {
        List<CityVertex> cities = routeService.initRouting();
        CityVertex fromCity = getByNameOrThrow(cities, fromCityName);
        CityVertex toCity = getByNameOrThrow(cities, toCityName);

        List<Path> paths = PathCalculator.calculateAllPaths(fromCity, toCity);
        if (paths.isEmpty()) {
            throw new PathNotFoundException(fromCityName, toCityName);
        }
        return paths;
    }

    private CityVertex getByNameOrThrow(List<CityVertex> cities, String name) {
        return cities.stream()
                .filter(city -> name.equals(city.getName()))
                .findFirst()
                .orElseThrow(() -> new CityNotFoundException(name));
    }

}
