package com.isp.project.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Customer")

public class Customer {
    @Id
    @Column(name = "CustomerID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int CustomerID;

    @Column(name = "CustomerName")
    private String CustomerName;

    @Column(name="CustomerAddress")
 private String CustomerAddress;

 @Column(name = "CustomerPhone")
 private String CustomerPhone;

 @Column(name = "CustomerEmail")
 private String CustomerEmail;

 @Column(name = "CustomerIdentificationID")
 private String CustomerIdentificationID;

 @Column(name = "CustomerDob")
 private Date CustomerDob;

 
public Customer() {
}

public Customer(int customerID, String customerName, String customerAddress, String customerPhone, String customerEmail,
        String customerIdentificationID, Date customerDob) {
    CustomerID = customerID;
    CustomerName = customerName;
    CustomerAddress = customerAddress;
    CustomerPhone = customerPhone;
    CustomerEmail = customerEmail;
    CustomerIdentificationID = customerIdentificationID;
    CustomerDob = customerDob;
}

public int getCustomerID() {
    return CustomerID;
}

public void setCustomerID(int customerID) {
    CustomerID = customerID;
}

public String getCustomerName() {
    return CustomerName;
}

public void setCustomerName(String customerName) {
    CustomerName = customerName;
}

public String getCustomerAddress() {
    return CustomerAddress;
}

public void setCustomerAddress(String customerAddress) {
    CustomerAddress = customerAddress;
}

public String getCustomerPhone() {
    return CustomerPhone;
}

public void setCustomerPhone(String customerPhone) {
    CustomerPhone = customerPhone;
}

public String getCustomerEmail() {
    return CustomerEmail;
}

public void setCustomerEmail(String customerEmail) {
    CustomerEmail = customerEmail;
}

public String getCustomerIdentificationID() {
    return CustomerIdentificationID;
}

public void setCustomerIdentificationID(String customerIdentificationID) {
    CustomerIdentificationID = customerIdentificationID;
}

public Date getCustomerDob() {
    return CustomerDob;
}

public void setCustomerDob(Date customerDob) {
    CustomerDob = customerDob;
}
 

    



}
