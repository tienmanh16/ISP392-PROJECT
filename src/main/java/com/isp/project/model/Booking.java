package com.isp.project.model;

import java.sql.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "BookingID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingID;

    @ManyToOne
    @JoinColumn(name = "CustomerID")
    private Customer customerID;

    @Column(name = "BookingDate")
    private Date bookingDate;

    @Column(name = "CustomerQuantity")
    private int customerQuantity;

    @OneToMany(mappedBy = "bookingID")
    private Set<Register> register;

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    
    public Customer getCustomerID() {
        return customerID;
    }

    

    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getCustomerQuantity() {
        return customerQuantity;
    }

    public void setCustomerQuantity(int customerQuantity) {
        this.customerQuantity = customerQuantity;
    }

    public Booking() {
    }

    public Booking(int bookingID, Customer customerID, Date bookingDate, int customerQuantity) {
        this.bookingID = bookingID;
        this.customerID = customerID;
        this.bookingDate = bookingDate;
        this.customerQuantity = customerQuantity;
    }

    

}
