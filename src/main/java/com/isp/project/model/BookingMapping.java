package com.isp.project.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BookingMapping")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class BookingMapping {


    @Id
    @Column(name = "BookingMappingID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingMappingID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Room roomID;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookingID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Booking bookingID;

    @Column(name = "CheckInDate")
    private Date checkInDate;

    @Column(name = "CheckOutDate")
    private Date checkOutDate;

    @Column(name = "BookingTotalAmount")
    private int bookingTotalAmount;

    @Column(name = "BookingMappingActive", nullable = false)
    private int bookingMappingActive;
    
}
