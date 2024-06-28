package com.isp.project.dto;

import java.sql.Date;
import java.util.List;

public class BookingInfoDTO {
    private String customerName;
    private String customerIdentificationID;
    private String customerPhone;
    private String customerEmail;
    private String customerAddress;
    private String gender;
    private Date checkinDate;
    private String checkinTime;
    private Date checkoutDate;
    private String checkoutTime;
    private int employeeId;
    private int customerQuantity;
    private String selectedRoomsJson;

    public BookingInfoDTO() {
    }

    public BookingInfoDTO(String customerName, String customerIdentificationID, String customerPhone,
            String customerEmail, String customerAddress, String gender, Date checkinDate, String checkinTime,
            Date checkoutDate, String checkoutTime, int employeeId, int customerQuantity) {
        this.customerName = customerName;
        this.customerIdentificationID = customerIdentificationID;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
        this.gender = gender;
        this.checkinDate = checkinDate;
        this.checkinTime = checkinTime;
        this.checkoutDate = checkoutDate;
        this.checkoutTime = checkoutTime;
        this.employeeId = employeeId;
        this.customerQuantity = customerQuantity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIdentificationID() {
        return customerIdentificationID;
    }

    public void setCustomerIdentificationID(String customerIdentificationID) {
        this.customerIdentificationID = customerIdentificationID;
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

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public void setCheckinTime(String checkinTime) {
        this.checkinTime = checkinTime;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public void setCheckoutTime(String checkoutTime) {
        this.checkoutTime = checkoutTime;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getCustomerQuantity() {
        return customerQuantity;
    }

    public void setCustomerQuantity(int customerQuantity) {
        this.customerQuantity = customerQuantity;
    }

    public String getSelectedRoomsJson() {
        return selectedRoomsJson;
    }

    public void setSelectedRoomsJson(String selectedRoomsJson) {
        this.selectedRoomsJson = selectedRoomsJson;
    }


}
