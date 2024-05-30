package com.isp.project.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceDetailDTO {
    private int InvoiceID;
    private double InvoiceTotalAmount;
    private String CustomerName;    
    private Date InvoiceDate;
    private double LineTotalAmount;
    private int Quantity;
    private String SeName;
    private double SePrice;
    private Date CheckInDate;
    private Date CheckOutDate;
    private String RoomNumber;
}
