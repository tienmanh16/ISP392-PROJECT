package com.isp.project.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.isp.project.model.Booking;
import com.isp.project.model.Invoice;
import com.isp.project.model.Service;
import com.isp.project.model.Service;

public interface InvoiceService {
    List<Invoice> getAllInvoice();
    List<Invoice> searchInvoice(String key);
    List<Invoice> searchInvoice(Date keyDate);
    // List<InvoiceDetailDTO> findInvoiceDetail(int InvoiceID);
    public String htmlToPdf(String processedHtml);
    public Booking getInfoInvoice(int invoiceID);
    public List<Service> listService(int invoiceID); 
    double getTotalInvoiceForMonth(int month, int year);
    Map<String, Double> getTotalServiceByYear(int year);
}
