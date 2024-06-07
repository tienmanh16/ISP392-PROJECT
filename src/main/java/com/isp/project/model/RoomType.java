package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.isp.project.validation.NoSpecialCharacter;
import com.isp.project.validation.PositiveValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
    @NoSpecialCharacter
    @NotBlank(message = "Room type name is mandatory")
    private String name;

    @Column(name = "Description")
    @NoSpecialCharacter
    @NotBlank(message = "Room type description is mandatory")
    private String des;

    @Column(name = "PricePerHour")
    @PositiveValue(message = "Price per hour must be positive")
    private int priceHour;

    @Column(name = "PricePerDay")
    @PositiveValue(message = "Price per day must be positive")
    private int priceDay;

    @OneToMany(mappedBy = "roomType")
    @JsonManagedReference
    private List<Room> room;

    @OneToMany(mappedBy = "roomType")
    @JsonManagedReference
    private List<ImageDetail> imageDetail;
}
