package com.itechart.pathfinder.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class Direction implements Serializable {

    private static final long serialVersionUID = -4455773750573921L;

    CityVertex target;
    double distance;

}
