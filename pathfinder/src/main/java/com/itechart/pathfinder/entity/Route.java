package com.itechart.pathfinder.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "route")
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
