package com.isp.project.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.Invoice;
import com.isp.project.repositories.InvoiceRepository;
@Service
public class InvoiceServiceImpl implements InvoiceService{
@Autowired
    private InvoiceRepository invoiceRepository;
    @Override
    public List<Invoice> getAllInvoice() {
     return this.invoiceRepository.findAll();
    }
    @Override
    public List<Invoice> searchInvoice(String key) {
     return this.invoiceRepository.searchInvoice(key);
    }
    @Override
    public List<Invoice> searchInvoice(Date keyDate) {
        return this.invoiceRepository.searchInvoice(keyDate);
    }
    @Override
    public List<InvoiceDetailDTO> findInvoiceDetail(int InvoiceID) {
        List<Object[]> rawResults = invoiceRepository.findInvoiceDetail(InvoiceID); // Ensure you are using the
                                                                                           // correct method name
        List<InvoiceDetailDTO> invoiceDetailDTOs = new ArrayList<>();
        for (Object[] rawResult : rawResults) {
            // Extracting data from the rawResult array and casting it to the appropriate
            // types
            int invoiceID = ((Number) rawResult[0]).intValue(); // InvoiceID
            double invoiceTotalAmount = ((Number) rawResult[1]).doubleValue(); // InvoiceTotalAmount
            String customerName = (String) rawResult[2]; // CustomerName
            Date invoiceDate = (Date) rawResult[3]; // InvoiceDate
            double lineTotalAmount = ((Number) rawResult[4]).doubleValue(); // LineTotalAmount
            int quantity = ((Number) rawResult[5]).intValue(); // Quantity
            String seName = (String) rawResult[6]; // SeName
            double sePrice = ((Number) rawResult[7]).doubleValue(); // SePrice
            Date checkInDate = (Date) rawResult[8]; // CheckInDate
            Date checkOutDate = (Date) rawResult[9]; // CheckOutDate
            String roomNumber = ((String) rawResult[10]);
            // Create a new InvoiceDetailDTO with the extracted data
            InvoiceDetailDTO dto = new InvoiceDetailDTO(invoiceID, invoiceTotalAmount, customerName, invoiceDate, lineTotalAmount, quantity, seName, sePrice, checkInDate, checkOutDate,roomNumber);
            invoiceDetailDTOs.add(dto);
        }
    
        return invoiceDetailDTOs;
    }
    
}
