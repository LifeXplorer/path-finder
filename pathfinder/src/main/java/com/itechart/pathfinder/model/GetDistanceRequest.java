package com.itechart.pathfinder.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetDistanceRequest {

    String cityNameFrom;
    String cityNameTo;
}
