package com.isp.project.service;

import java.util.List;

import com.isp.project.model.Invoice;

public interface InvoiceService {
    List<Invoice> getAllInvoice();
    List<Invoice> searchInvoice(String key);
}
