package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RoomType")
public class RoomType {
    @Id
    @Column(name = "RoomTypeID")
    private int RoomTypeID;

    @Column(name = "Description")
    private String Description;

    

    @Column(name = "PricePerHour")
    private int PricePerHour;

    @Column(name = "PricePerDay")
    private int PricePerDay;
    
    @ManyToOne
    @JoinColumn(name="ImageID")
    private ImageDetail imageDetail;
    
    public RoomType() {
    }

    public RoomType(int roomTypeID, String description, int pricePerHour, int pricePerDay, ImageDetail imageDetail) {
        RoomTypeID = roomTypeID;
        Description = description;
        PricePerHour = pricePerHour;
        PricePerDay = pricePerDay;
        this.imageDetail = imageDetail;
    }

   
}
