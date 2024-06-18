package com.isp.project.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ServiceType")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SeTypeID", nullable = false)
    private int SeTypeID;

    @Column(name = "SeTypeName")
    private String SeTypeName;

    @Column(name = "ServiceTypeActive", nullable = false)
    private int serviceActive;
    
    @OneToMany(mappedBy = "serviceType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Service> service;
    


}