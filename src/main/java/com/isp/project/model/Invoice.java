package com.isp.project.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Entity 
@Getter
@Setter
@Table(name="Invoice")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice {
    @Id
    @Column(name = "InvoiceID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int InvoiceID;
    
    @Column(name="TotalAmount")
    private double TotalAmount;

    @Column(name = "CustomerName")
    private String CustomerName;

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookingID")
    @JsonBackReference
    private Booking booking;



    @Column(name = "InvoiceDate")
    private Date InvoiceDate;

    
    @OneToMany(mappedBy = "invoice")
    @JsonManagedReference
    private List<InvoiceLine> invoiceLine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BookingMappingID")
    private BookingMapping bookingMapping;
    

}
