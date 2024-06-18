package com.isp.project.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomCustomerDTO {
    private int bookingMappingId;
    private Date checkInDate;
    private Date checkOutDate;
    private int bookingTotalAmount;
    private String roomNumber;
    private String roomStatus;
    private Date bookingDate;
    private int customerQuantity;
    private String customerName;
    private String customerAddress;
    private String customerPhone;
    private String customerEmail;
    private String customerIdentificationID;
    private String customerGender;

}
