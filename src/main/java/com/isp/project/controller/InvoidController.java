package com.isp.project.controller;

import java.sql.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.isp.project.dto.InvoiceDetailDTO;
import com.isp.project.model.Invoice;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.service.InvoiceService;


 @Controller

public class InvoidController {
    

    @Autowired
    private InvoiceService invoiceService;


    @GetMapping("/listinvoice")
    public String listInvoice(Model model, @Param("key")String key, @Param("keyDate") Date keyDate){
          
        List<Invoice> invoice;

        if (key != null) {
            invoice = this.invoiceService.searchInvoice(key);
        } else if (keyDate != null) {
            invoice = this.invoiceService.searchInvoice(keyDate);
        } else {
            // Nếu không có key hoặc keyDate, hiển thị tất cả hóa đơn
            invoice = this.invoiceService.getAllInvoice();
        }


        model.addAttribute("listInvoice", invoice);
        return "listInvoice.html"; 
    }  
    
    // @GetMapping("/invoiceDetail/{invoiceID}")
    // public String invoice(Model model, @PathVariable("invoiceID") int invoiceID){    
    //     List<InvoiceDetailDTO> invoiceDetailDTO = this.invoiceService.findInvoiceDetail(invoiceID);  
    //     model.addAttribute("InvoiceDetailDTO", invoiceDetailDTO);
    //     return "invoice.html"; 
    // }  
}
