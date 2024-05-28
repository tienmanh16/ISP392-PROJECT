package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Register")
public class Register {
    @Id
    @Column(name = "BookingID")
    private int BookingID;

    @Id
    @Column(name = "EmployeeID")
    private int EmployeeID;

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
    }

    public Register() {
    }

    public Register(int bookingID, int employeeID) {
        BookingID = bookingID;
        EmployeeID = employeeID;
    }

    
}
