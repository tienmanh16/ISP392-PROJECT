package com.isp.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ServiceType")
public class ServiceType {
    @Id
    @Column(name = "SeTypeID")
    private int SeTypeID;

    @Column(name = "SeTypeName")
    private String SeTypeName;
    


}
