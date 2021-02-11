package com.itechart.pathfinder.service;

import com.itechart.pathfinder.entity.City;
import com.itechart.pathfinder.entity.Route;
import com.itechart.pathfinder.model.CityEdge;
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
import java.util.Optional;

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
        var cityFrom = getExistingOrCreateCity(cityNameFrom);
        var cityTo = getExistingOrCreateCity(cityNameTo);
        routeRepository.getByFromCityAndToCity(cityFrom, cityTo)
                .ifPresentOrElse(city -> updateDistance(distance, city),
                        () -> createNewCity(distance, cityFrom, cityTo));
    }

    @Cacheable(INITIALIZED_ROUTING)
    @Transactional
    public List<City> initRouting() {
        List<City> cities = cityRepository.findAll();
        routeRepository.findAll()
                .forEach(route -> {
                    City fromCity = route.getFromCity();
                    City toCity = route.getToCity();
                    var fromCityNode = new CityEdge(toCity, route.getDistance());
                    fromCity.getAdjacencyList().add(fromCityNode);
                    var toCityNode = new CityEdge(fromCity, route.getDistance());
                    toCity.getAdjacencyList().add(toCityNode);
                });
        return cities;
    }

    private void updateDistance(double distance, Route city) {
        city.setDistance(distance);
        routeRepository.save(city);
    }

    private void createNewCity(double distance, City cityFrom, City cityTo) {
        var cityDistance = new Route();
        cityDistance.setDistance(distance);
        cityDistance.setFromCity(cityFrom);
        cityDistance.setToCity(cityTo);
        routeRepository.save(cityDistance);
    }

    private City getExistingOrCreateCity(String cityName) {
        Optional<City> byName = cityRepository.getByName(cityName);
        if (byName.isEmpty()) {
            return cityRepository.save(City.ofName(cityName));
        }
        return byName.get();
    }

}
