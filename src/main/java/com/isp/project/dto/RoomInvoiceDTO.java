package com.isp.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomInvoiceDTO {
    private int bookingMappingId;
    private int id;
    private String roomNum;
    private String roomTypeName;
    private String cleaning;
    private int bookingMappingActive;
    private int invoiceId;
}
