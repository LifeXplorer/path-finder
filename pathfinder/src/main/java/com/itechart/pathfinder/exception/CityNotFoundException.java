package com.itechart.pathfinder.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CityNotFoundException extends RuntimeException {

    String cityName;

    @Override
    public String getMessage() {
        return String.format("No %s city found!", cityName);
    }
}
