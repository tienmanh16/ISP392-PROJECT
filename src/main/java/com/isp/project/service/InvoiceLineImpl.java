package com.isp.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.InvoiceLine;
import com.isp.project.repositories.InvoiceLineRepository;

@Service
public class InvoiceLineImpl implements InvoiceLineService{
    @Autowired
    private InvoiceLineRepository invoiceLineRepository;



    @Override
    public Boolean createInvoiceLine(InvoiceLine invoiceLine) {
        try {
            this.invoiceLineRepository.save(invoiceLine);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

}
