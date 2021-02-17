package com.itechart.pathfinder.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "city")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class City extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    String name;

    public static City ofName(String name) {
        City city = new City();
        city.setName(name);
        return city;
    }

}
