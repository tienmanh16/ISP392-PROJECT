package com.isp.project.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="Invoice")
public class Invoice {
    @Id
    @Column(name = "InvoiceID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int InvoiceID;
    
    @Column(name="TotalAmount")
    private double TotalAmount;

    @Column(name = "CustomerName")
    private String CustomerName;

    @Column(name ="BookingID")
    private int BookingID;

    @Column(name = "InvoiceDate")
    private Date InvoiceDate;

    public int getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        InvoiceID = invoiceID;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        BookingID = bookingID;
    }

    public Date getInvoiceDate() {
        return InvoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        InvoiceDate = invoiceDate;
    }

    @Override
    public String toString() {
        return "Invoice [InvoiceID=" + InvoiceID + ", TotalAmount=" + TotalAmount + ", CustomerName=" + CustomerName
                + ", BookingID=" + BookingID + ", InvoiceDate=" + InvoiceDate + "]";
    }

    

}
