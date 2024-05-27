package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "Room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RoomID", nullable = false)
    private int id;

    @Column(name = "RoomNumber", nullable = false)
    private String roomNum;

    @ManyToOne
    @JoinColumn(name = "RoomTypeID")
    private RoomType roomType;

    @Column(name = "PricePerHour", nullable = false)
    private int priceHour;

    @Column(name = "PricePerDay", nullable = false)
    private int priceDay;

    @Column(name = "RoomStatus", nullable = false)
    private String status;

}
