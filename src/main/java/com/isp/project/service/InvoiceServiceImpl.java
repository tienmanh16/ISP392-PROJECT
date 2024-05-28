package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.isp.project.model.Invoice;
import com.isp.project.repositories.InvoiceRepository;

public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Override
    public List<Invoice> getAllInvoice() {
     return this.invoiceRepository.findAll();
    }
    
}
