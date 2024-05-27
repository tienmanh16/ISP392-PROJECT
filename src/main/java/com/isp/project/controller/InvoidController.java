package com.isp.project.controller;

import java.sql.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.isp.project.model.Invoice;
import com.isp.project.repositories.InvoiceRepository;


 @Controller

public class InvoidController {
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @GetMapping("/listinvoice")
    public String listInvoice(Model model, @Param("key")String key, @Param("keyDate") Date keyDate){
          
        List<Invoice> invoice;

        if (key != null) {
            invoice = this.invoiceRepository.searchInvoice(key);
        } else if (keyDate != null) {
            invoice = this.invoiceRepository.searchInvoice(keyDate);
        } else {
            // Nếu không có key hoặc keyDate, hiển thị tất cả hóa đơn
            invoice = this.invoiceRepository.findAll();
        }


        model.addAttribute("listInvoice", invoice);
        return "listInvoice.html"; 
    }  
    
    @GetMapping("/invoid")
    public String invoice(){      
        return "invoice.html"; 
    }  
}
