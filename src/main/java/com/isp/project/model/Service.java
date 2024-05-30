package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Service")
public class Service {
    @Id
    @Column(name="SeID")
    private int SeID;
    
    @Column(name="SeName")
    private String SeName;

    @Column(name="SePrice")
    private double SePrice;

    @ManyToOne
    @JoinColumn(name = "SeTypeID")
    private ServiceType serviceType;
    

}
