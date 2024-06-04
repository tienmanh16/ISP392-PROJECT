package com.isp.project.dto;

import java.sql.Date;




public class BookingRoomDTO {
    private int bookingID;
    private String customerName;
    private Date checkInDate;
    private Date checkOutDate;
    private Date bookingDate;
    private int customerQuantity;
    private int roomID;
    private String employeeName;

    public BookingRoomDTO() {
    }
    
    public BookingRoomDTO(int bookingID, String customerName, Date checkInDate, Date checkOutDate, Date bookingDate,
            int customerQuantity, int roomID, String employeeName) {
        this.bookingID = bookingID;
        this.customerName = customerName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.customerQuantity = customerQuantity;
        this.roomID = roomID;
        this.employeeName = employeeName;
    }
    public int getBookingID() {
        return bookingID;
    }
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Date getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }
    public Date getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public Date getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    public int getCustomerQuantity() {
        return customerQuantity;
    }
    public void setCustomerQuantity(int customerQuantity) {
        this.customerQuantity = customerQuantity;
    }
    public int getRoomID() {
        return roomID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    
}
