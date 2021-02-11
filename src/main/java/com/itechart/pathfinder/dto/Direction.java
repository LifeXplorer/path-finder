package com.itechart.pathfinder.dto;

import com.itechart.pathfinder.entity.City;
import lombok.Value;

@Value
public class Direction {

    City target;
    double distance;

}
