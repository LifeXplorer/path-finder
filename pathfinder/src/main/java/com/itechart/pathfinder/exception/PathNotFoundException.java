package com.itechart.pathfinder.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PathNotFoundException extends RuntimeException {

    String fromCity;
    String toCity;

    @Override
    public String getMessage() {
        return String.format("No path found between %s and %s", fromCity, toCity);
    }
}
