package com.isp.project.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Booking")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Booking {
    @Id
    @Column(name = "BookingID")
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Customer customerID;

    @Column(name = "BookingDate")
    private Date bookingDate;

    @Column(name = "CustomerQuantity")
    private int customerQuantity;

    @Column(name = "BookingStatus", nullable = false)
    private int isCancelled;

    // @OneToMany(mappedBy = "bookingID")
    // @JsonManagedReference
    @OneToMany(mappedBy = "bookingID", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("bookingID")
    private List<Register> register;

    @OneToMany(mappedBy = "bookingID")
    @JsonManagedReference
    private List<BookingMapping> bookingMapping;

    @OneToMany(mappedBy = "booking")
    @JsonManagedReference
    private List<Invoice> invoice;

}