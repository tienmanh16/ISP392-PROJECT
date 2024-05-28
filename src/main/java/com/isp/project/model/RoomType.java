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

    @ManyToOne
    @JoinColumn(name="ImageID")
    private ImageDetail imageDetail;
    

    @Column(name = "PricePerHour")
    private int PricePerHour;

    @Column(name = "PricePerDay")
    private int PricePerDay;
    
    
    public RoomType() {
    }

    public RoomType(int roomTypeID, String description, int pricePerHour, int pricePerDay, ImageDetail imageDetail) {
        RoomTypeID = roomTypeID;
        Description = description;
        PricePerHour = pricePerHour;
        PricePerDay = pricePerDay;
        this.imageDetail = imageDetail;
    }

    public int getRoomTypeID() {
        return RoomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        RoomTypeID = roomTypeID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ImageDetail getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(ImageDetail imageDetail) {
        this.imageDetail = imageDetail;
    }

    public int getPricePerHour() {
        return PricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        PricePerHour = pricePerHour;
    }

    public int getPricePerDay() {
        return PricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        PricePerDay = pricePerDay;
    }
    
   
}
