package com.itechart.pathfinder.service;

import com.itechart.pathfinder.dto.Direction;
import com.itechart.pathfinder.entity.City;
import com.itechart.pathfinder.entity.Route;
import com.itechart.pathfinder.repository.CityRepository;
import com.itechart.pathfinder.repository.RouteRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.PriorityQueue;

import static com.itechart.pathfinder.cache.CacheNamesContainer.INITIALIZED_ROUTING;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RouteService {

    RouteRepository routeRepository;
    CityRepository cityRepository;

    @CacheEvict(value = INITIALIZED_ROUTING, allEntries = true)
    @Transactional
    public void upsertRoute(String cityNameFrom, String cityNameTo, double distance) {
        PriorityQueue<String> sortedNamesQueue = new PriorityQueue<>(2);
        sortedNamesQueue.add(cityNameFrom);
        sortedNamesQueue.add(cityNameTo);
        City cityFrom = getExistingOrCreateCity(sortedNamesQueue.poll());
        City cityTo = getExistingOrCreateCity(sortedNamesQueue.poll());
        routeRepository.getByFromCityAndToCity(cityFrom, cityTo)
                .ifPresentOrElse(route -> updateRouteDistance(route, distance),
                        () -> createRoute(cityFrom, cityTo, distance));
    }

    @Cacheable(INITIALIZED_ROUTING)
    @Transactional
    public List<City> initRouting() {
        List<City> cities = cityRepository.findAll();
        routeRepository.findAll()
                .forEach(route -> {
                    City fromCity = route.getFromCity();
                    City toCity = route.getToCity();
                    Direction fromCityNode = new Direction(toCity, route.getDistance());
                    Direction toCityNode = new Direction(fromCity, route.getDistance());
                    fromCity.getDirections().add(fromCityNode);
                    toCity.getDirections().add(toCityNode);
                });
        return cities;
    }

    private void updateRouteDistance(Route city, double distance) {
        city.setDistance(distance);
        routeRepository.save(city);
    }

    private void createRoute(City cityFrom, City cityTo, double distance) {
        Route route = new Route();
        route.setFromCity(cityFrom);
        route.setToCity(cityTo);
        route.setDistance(distance);
        routeRepository.save(route);
    }

    private City getExistingOrCreateCity(String cityName) {
        return cityRepository.getByName(cityName)
                .orElseGet(() -> cityRepository.save(City.ofName(cityName)));
    }

}
