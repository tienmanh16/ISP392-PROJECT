package com.isp.project.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Booking")
public class Booking {
    @Id
    @Column(name = "BookingID")
    private int BookingID;

    @Column(name = "CustomerID")
    private int CustomerID;

    @Column(name = "BookingDate")
    private Date BookingDate;
    

    
}
