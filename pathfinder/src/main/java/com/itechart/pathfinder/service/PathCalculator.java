package com.itechart.pathfinder.service;

import com.itechart.pathfinder.entity.City;
import com.itechart.pathfinder.model.CityEdge;
import com.itechart.pathfinder.model.Path;
import lombok.experimental.UtilityClass;

import java.util.*;

@UtilityClass
public class PathCalculator {

    public static List<Path> calculateAllPaths(City sourceCity, City targetCity) {
        return getAllPaths(targetCity, 0.0, sourceCity, new HashSet<>());
    }

    private List<Path> getAllPaths(City targetVertex, Double totalPath, City startVertex, Set<City> visitedCities) {
        visitedCities.add(targetVertex);
        List<Path> retLists = new ArrayList<>();
        if (targetVertex.equals(startVertex)) {
            List<String> leafList = new LinkedList<>();
            leafList.add(targetVertex.getName());
            retLists.add(new Path(leafList, totalPath));
        } else {
            for (CityEdge edge : targetVertex.getAdjacencyList()) {
                if (visitedCities.contains(edge.getTargetCity())) {
                    continue;
                }
                List<Path> nodeLists = getAllPaths(edge.getTargetCity(), edge.getDistance(), startVertex, visitedCities);
                for (Path path : nodeLists) {
                    path.getCities().add(targetVertex.getName());
                    path.setDistance(path.getDistance() + totalPath);
                    retLists.add(path);
                }
                visitedCities.remove(edge.getTargetCity());
            }
        }
        return retLists;
    }
}
