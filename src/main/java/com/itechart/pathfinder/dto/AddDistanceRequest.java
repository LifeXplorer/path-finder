package com.itechart.pathfinder.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddDistanceRequest {

    @NotBlank
    String cityNameFrom;
    @NotBlank
    String cityNameTo;
    @Positive
    @NotNull
    Double distance;
}
