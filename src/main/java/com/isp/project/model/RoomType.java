package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "RoomType")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class RoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomTypeID", nullable = false)
    private int id;

    @Column(name = "RoomTypeName")
    private String name;

    @Column(name = "Description")
    private String des;

    @Column(name = "PricePerHour")
    private int priceHour;

    @Column(name = "PricePerDay")
    private int priceDay;

    @OneToMany(mappedBy = "roomType")
    @JsonManagedReference
    private List<Room> room;

    @OneToMany(mappedBy = "roomType")
    @JsonManagedReference
    private List<ImageDetail> imageDetail;
}
