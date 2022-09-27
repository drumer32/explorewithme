package com.drumer32.explorewithme.tools;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer lat;
    Integer lon;
}
