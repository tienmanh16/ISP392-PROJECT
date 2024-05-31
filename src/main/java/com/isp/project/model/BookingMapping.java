package com.isp.project.model;

import java.sql.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "BookingMapping")
public class BookingMapping {


    @Id
    @Column(name = "BookingMappingID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingMappingID;

    @ManyToOne
    @JoinColumn(name = "RoomID")
    private Room roomID;

    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking bookingID;

    @Column(name = "CheckInDate")
    private Date checkInDate;

    @Column(name = "CheckOutDate")
    private Date checkOutDate;

    @Column(name = "BookingTotalAmount")
    private int bookingTotalAmount;

    public BookingMapping() {
    }

    public BookingMapping(int bookingMappingID, Room roomID, Booking bookingID, Date checkInDate, Date checkOutDate,
            int bookingTotalAmount) {
        this.bookingMappingID = bookingMappingID;
        this.roomID = roomID;
        this.bookingID = bookingID;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingTotalAmount = bookingTotalAmount;
    }

    public int getBookingMappingID() {
        return bookingMappingID;
    }

    public void setBookingMappingID(int bookingMappingID) {
        this.bookingMappingID = bookingMappingID;
    }

    public Room getRoomID() {
        return roomID;
    }

    public void setRoomID(Room roomID) {
        this.roomID = roomID;
    }

    public Booking getBookingID() {
        return bookingID;
    }

    public void setBookingID(Booking bookingID) {
        this.bookingID = bookingID;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getBookingTotalAmount() {
        return bookingTotalAmount;
    }

    public void setBookingTotalAmount(int bookingTotalAmount) {
        this.bookingTotalAmount = bookingTotalAmount;
    }

    
    
}
