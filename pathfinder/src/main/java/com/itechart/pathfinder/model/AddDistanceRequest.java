package com.itechart.pathfinder.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddDistanceRequest {

    String cityNameFrom;
    String cityNameTo;
    double distance;
}
