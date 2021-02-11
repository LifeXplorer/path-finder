package com.itechart.pathfinder.entity;

import com.itechart.pathfinder.dto.Direction;
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
    List<Direction> directions;

    public static City ofName(String name) {
        City city = new City();
        city.setName(name);
        return city;
    }

    public List<Direction> getDirections() {
        if (directions == null) {
            directions = new ArrayList<>();
        }
        return directions;
    }

}
