package com.itechart.pathfinder.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddDistanceRequest {

    String cityNameFrom;
    String cityNameTo;
    double distance;
}
