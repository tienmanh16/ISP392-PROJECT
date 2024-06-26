package com.isp.project.service;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.isp.project.model.Booking;
import com.isp.project.model.Invoice;
import com.isp.project.model.Service;

public interface InvoiceService {
    List<Invoice> getAllInvoice();
    Page<Invoice> searchInvoice(Integer pageNo, String key);
    Page<Invoice> searchInvoice(Integer pageNo, Date keyDate);
    // List<InvoiceDetailDTO> findInvoiceDetail(int InvoiceID);
    String htmlToPdf(String processedHtml, String name);
    Booking getInfoInvoice(int invoiceID);
    List<Service> listService(int invoiceID); 
    double getTotalInvoiceForMonth(int month, int year);
    Map<String, Double> getTotalServiceByYear(int year);
    Page<Invoice> pageInvoice(Integer pageNo);
    void htmlToWord(String html, String filePath);
}
