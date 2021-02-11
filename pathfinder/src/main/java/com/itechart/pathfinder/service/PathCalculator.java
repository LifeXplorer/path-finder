package com.itechart.pathfinder.service;

import com.itechart.pathfinder.dto.Direction;
import com.itechart.pathfinder.dto.Path;
import com.itechart.pathfinder.entity.City;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@UtilityClass
public class PathCalculator {

    private static final double ZERO_DISTANCE = 0.0;

    public static List<Path> calculateAllPaths(City sourceCity, City targetCity) {
        return getAllPaths(sourceCity, targetCity, ZERO_DISTANCE, new HashSet<>());
    }

    private List<Path> getAllPaths(City sourceCity, City targetCity, Double totalDistance, Set<City> visitedCities) {
        visitedCities.add(targetCity);
        List<Path> paths = new ArrayList<>();
        if (targetCity.equals(sourceCity)) {
            List<String> citiesRoute = new LinkedList<>();
            citiesRoute.add(targetCity.getName());
            paths.add(new Path(citiesRoute, totalDistance));
        } else {
            for (Direction direction : targetCity.getDirections()) {
                if (visitedCities.contains(direction.getTarget())) {
                    continue;
                }
                List<Path> nodeLists =
                        getAllPaths(sourceCity, direction.getTarget(), direction.getDistance(), visitedCities);
                for (Path path : nodeLists) {
                    path.getCities().add(targetCity.getName());
                    path.setDistance(path.getDistance() + totalDistance);
                    paths.add(path);
                }
                visitedCities.remove(direction.getTarget());
            }
        }
        return paths;
    }
}
