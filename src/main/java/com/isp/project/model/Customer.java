package com.isp.project.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Customer")

public class Customer {

    @Id
    @Column(name = "CustomerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int customerID;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name="CustomerAddress")
 private String customerAddress;

 @Column(name = "CustomerPhone")
 private String customerPhone;

 @Column(name = "CustomerEmail")
 private String customerEmail;

 @Column(name = "CustomerIdentificationID")
 private String customerIdentificationID;

 @Column(name = "CustomerDob")
 private Date customerDob;

  @OneToMany(mappedBy = "customerID")
  private List<Booking> booking;

public Customer() {
}

public Customer(int customerID, String customerName, String customerAddress, String customerPhone, String customerEmail,
        String customerIdentificationID, Date customerDob) {
    this.customerID = customerID;
    this.customerName = customerName;
    this.customerAddress = customerAddress;
    this.customerPhone = customerPhone;
    this.customerEmail = customerEmail;
    this.customerIdentificationID =customerIdentificationID;
    this.customerDob = customerDob;
}

public int getCustomerID() {
    return customerID;
}

public void setCustomerID(int customerID) {
    this.customerID = customerID;
}

public String getCustomerName() {
    return customerName;
}

public void setCustomerName(String customerName) {
    this.customerName = customerName;
}

public String getCustomerAddress() {
    return customerAddress;
}

public void setCustomerAddress(String customerAddress) {
    this.customerAddress = customerAddress;
}

public String getCustomerPhone() {
    return customerPhone;
}

public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
}

public String getCustomerEmail() {
    return customerEmail;
}

public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
}

public String getCustomerIdentificationID() {
    return customerIdentificationID;
}

public void setCustomerIdentificationID(String customerIdentificationID) {
    this.customerIdentificationID = customerIdentificationID;
}

public Date getCustomerDob() {
    return customerDob;
}

public void setCustomerDob(Date customerDob) {
    this.customerDob = customerDob;
}
 

    



}
