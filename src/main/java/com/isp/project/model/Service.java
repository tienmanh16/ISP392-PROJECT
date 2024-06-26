package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Service")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Service {
    @Id
    @Column(name="SeID")
    private int SeID;
    
    @Column(name="SeName")
    private String SeName;

    @Column(name="SePrice")
    private double SePrice;

    @Column(name = "ServiceActive", nullable = false)
    private int serviceActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SeTypeID")
    @JsonBackReference
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private ServiceType serviceType;
    
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<InvoiceLine> invoiceLine;
    
}