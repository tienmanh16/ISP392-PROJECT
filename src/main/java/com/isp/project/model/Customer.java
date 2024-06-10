package com.isp.project.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Customer")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Customer {

  @Id
  @Column(name = "CustomerID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int customerID;

  @Column(name = "CustomerName")
  private String customerName;

  @Column(name = "CustomerGender")
  private String customerGender;

  @Column(name = "CustomerAddress")
  private String customerAddress;

  @Column(name = "CustomerPhone")
  private String customerPhone;

  @Column(name = "CustomerEmail")
  private String customerEmail;

  @Column(name = "CustomerIdentificationID")
  private String customerIdentificationID;

  @OneToMany(mappedBy = "customerID")
  @JsonManagedReference
  private List<Booking> booking;

}
