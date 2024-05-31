package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "RoomType")
public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RoomTypeID", nullable = false)
    private int id;

    @Column(name = "RoomTypeName", nullable = false)
    private String name;

    @Column(name = "Description", nullable = false)
    private String des;

    @Column(name = "PricePerHour", nullable = false)
    private String priceHour;

    @Column(name = "PricePerDay", nullable = false)
    private String priceDay;

}
