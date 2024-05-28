package com.isp.project.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Booking")
public class Booking {
    @Id
    @Column(name = "BookingID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BookingID;

    @Column(name = "CustomerID")
    private int CustomerID;

    @Column(name = "BookingDate")
    private Date BookingDate;

    @Column(name = "CustomerQuantity")
    private int CustomerQuantity;

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public int getCustomerQuantity() {
        return CustomerQuantity;
    }

    public void setCustomerQuantity(int customerQuantity) {
        CustomerQuantity = customerQuantity;
    }

    public Booking() {
    }

    public Booking(int bookingID, int customerID, Date bookingDate, int customerQuantity) {
        BookingID = bookingID;
        CustomerID = customerID;
        BookingDate = bookingDate;
        CustomerQuantity = customerQuantity;
    }

    

}
