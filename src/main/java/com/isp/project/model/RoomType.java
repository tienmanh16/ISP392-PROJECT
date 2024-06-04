package com.isp.project.model;

import java.util.List;

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
    private int priceHour;

    @Column(name = "PricePerDay", nullable = false)
    private int priceDay;

   @OneToMany(mappedBy = "roomType")
   @JsonManagedReference
   private List<ImageDetail> imageDetail;

   @OneToMany(mappedBy = "roomType")
   @JsonManagedReference
   private List<Room> room; 


}
