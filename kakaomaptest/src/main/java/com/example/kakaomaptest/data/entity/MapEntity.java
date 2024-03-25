package com.example.kakaomaptest.data.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Favorites")
@Getter
@Setter
public class MapEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // auto_increment
    private Integer favorites_tbl_id;

    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "chargingstation_id")
    private String chargingstation_id;
}
