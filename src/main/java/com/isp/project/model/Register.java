package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="Register")
public class Register {
    @Id
    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking bookingID;

    @Id
    @ManyToOne
    @JoinColumn(name = "EmployeeID")
    private Employee employeeID;

    
    
}
