package com.isp.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceLineDTO {
    private double InvoiceTotalAmount;
    private int SeID;
    private int Quantity;
    private int InvoiceID;
}
