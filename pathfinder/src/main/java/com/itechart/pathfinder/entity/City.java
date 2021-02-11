package com.itechart.pathfinder.entity;

import com.itechart.pathfinder.model.CityEdge;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "city")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class City extends BaseEntity{

    @Column(name = "name", unique = true, nullable = false)
    String name;

    @Transient
    List<CityEdge> adjacencyList;

    public static City ofName(String name) {
        City city = new City();
        city.setName(name);
        return city;
    }

    public List<CityEdge> getAdjacencyList() {
        if (adjacencyList == null) {
            adjacencyList = new ArrayList<>();
        }
        return adjacencyList;
    }

}
