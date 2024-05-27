package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.isp.project.model.Invoice;
import com.isp.project.repositories.InvoiceRepository;


 @Controller

public class InvoidController {
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @GetMapping("/listinvoid")
    public String listInvoice(Model model){  
        List<Invoice> invoice = this.invoiceRepository.findAll();
        model.addAttribute("listInvoice", invoice);
        return "listInvoid.html"; 
    }  
    
    @GetMapping("/invoid")
    public String invoice(){      
        return "invoice.html"; 
    }  
}
