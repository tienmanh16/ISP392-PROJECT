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

    @Column(name = "RoomTypeName")
    private String RoomTypeName;

    @Column(name = "Description")
    private String Description;

    @ManyToOne
    @JoinColumn(name="ImageID")
    private ImageDetail imageDetail;
    

    @Column(name = "PricePerHour")
    private String PricePerHour;

    @Column(name = "PricePerDay")
    private String PricePerDay;
    
    
    public RoomType() {
    }


    public int getRoomTypeID() {
        return RoomTypeID;
    }


    public void setRoomTypeID(int roomTypeID) {
        RoomTypeID = roomTypeID;
    }


    public String getRoomTypeName() {
        return RoomTypeName;
    }


    public void setRoomTypeName(String roomTypeName) {
        RoomTypeName = roomTypeName;
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


    public String getPricePerHour() {
        return PricePerHour;
    }


    public void setPricePerHour(String pricePerHour) {
        PricePerHour = pricePerHour;
    }


    public String getPricePerDay() {
        return PricePerDay;
    }


    public void setPricePerDay(String pricePerDay) {
        PricePerDay = pricePerDay;
    }

    
   
}
