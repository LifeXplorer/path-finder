package com.itechart.pathfinder.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "route", uniqueConstraints =
        {@UniqueConstraint(name = "route_cities_unique_idx", columnNames = {"from_city_id", "to_city_id"})})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Route extends BaseEntity {

    @Column(name = "distance")
    double distance;

    @ManyToOne
    @JoinColumn(name = "from_city_id")
    City fromCity;

    @ManyToOne
    @JoinColumn(name = "to_city_id")
    City toCity;

}
