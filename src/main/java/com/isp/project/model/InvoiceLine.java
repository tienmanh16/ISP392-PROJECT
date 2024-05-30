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
@Table(name = "InvoiceLine")
public class InvoiceLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "InvoidLineID")
    private int InvoiceLineID;

    @Column(name = "TotalAmount")
    private double InvoiceTotalAmount;

    @ManyToOne
    @JoinColumn(name = "SeID")
    private Service service;

    @Column(name = "Quantity")
    private int Quantity;


    @ManyToOne
    @JoinColumn(name = "InvoiceID")
    private Invoice invoice;
    






}
