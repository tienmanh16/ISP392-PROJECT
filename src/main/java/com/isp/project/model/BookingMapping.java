package com.isp.project.model;

import java.sql.Date;

import javax.xml.crypto.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BookingMapping")
public class BookingMapping {


    @Id
    @Column(name = "BookingMappingID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BookingMappingID;

    @Column(name = "RoomID")
    private int RoomID;

    @Column(name = "BookingID")
    private int BookingID;

    @Column(name = "CheckInDate")
    private Date CheckInDate;

    @Column(name = "CheckOutDate")
    private Date CheckOutDate;

    @Column(name = "BookingTotalAmount")
    private int BookingTotalAmount;

    public int getBookingMappingID() {
        return BookingMappingID;
    }

    public void setBookingMappingID(int bookingMappingID) {
        BookingMappingID = bookingMappingID;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public Date getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        CheckInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public int getBookingTotalAmount() {
        return BookingTotalAmount;
    }

    public void setBookingTotalAmount(int bookingTotalAmount) {
        BookingTotalAmount = bookingTotalAmount;
    }

    public BookingMapping() {
    }

    public BookingMapping(int bookingMappingID, int roomID, int bookingID, Date checkInDate, Date checkOutDate,
            int bookingTotalAmount) {
        BookingMappingID = bookingMappingID;
        RoomID = roomID;
        BookingID = bookingID;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        BookingTotalAmount = bookingTotalAmount;
    }

    
}
