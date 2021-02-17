package com.itechart.pathfinder.dto;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CityVertex implements Serializable {

    private static final long serialVersionUID = 4883781038713914258L;

    @EqualsAndHashCode.Include
    String name;

    List<Direction> directions = new ArrayList<>();

    public static CityVertex ofName(String name) {
        CityVertex cityVertex = new CityVertex();
        cityVertex.setName(name);
        return cityVertex;
    }

}
