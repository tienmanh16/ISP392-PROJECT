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
import lombok.Data;
@Data
@Entity 
@Table(name="Invoice")
public class Invoice {
    @Id
    @Column(name = "InvoiceID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int InvoiceID;
    
    @Column(name="TotalAmount")
    private double TotalAmount;

    @Column(name = "CustomerName")
    private String CustomerName;

    @ManyToOne
    @JoinColumn(name = "BookingID")
    private Booking booking;

    @Column(name = "InvoiceDate")
    private Date InvoiceDate;

   
    

}
