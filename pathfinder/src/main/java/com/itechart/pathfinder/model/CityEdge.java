package com.itechart.pathfinder.model;

import com.itechart.pathfinder.entity.City;
import lombok.Value;

@Value
public class CityEdge {

    City targetCity;
    double distance;

}
