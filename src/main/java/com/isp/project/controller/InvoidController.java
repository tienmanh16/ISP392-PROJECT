package com.isp.project.controller;

import java.sql.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.isp.project.dto.InvoiceDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Service;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;

@Controller

public class InvoidController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/listinvoice")
    public String listInvoice(Model model, @Param("key") String key, @Param("keyDate") Date keyDate) {

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


    @GetMapping("/printInvoice/{invoiceID}")
    public String printInvoice(@PathVariable("invoiceID") int invoiceID, Model model) {
        // Fetch the invoice details
        Booking booking = invoiceService.getInfoInvoice(invoiceID);
        double totalSePrice = 0.0;
        double totalAmountRoom = 0.0;
        List<Service> serviceList = invoiceServiceImpl.listService(invoiceID);

        for (Service service : serviceList) {
            for (InvoiceLine invoiceLine : service.getInvoiceLine()) {
                totalSePrice += invoiceLine.getInvoiceTotalAmount();
            }
        }
        for (BookingMapping ls : booking.getBookingMapping()) {
            totalAmountRoom += ls.getBookingTotalAmount();
        }
        // Prepare context for Thymeleaf
        Context context = new Context();
        context.setVariable("booking", booking);
        context.setVariable("totalAmountRoom", totalAmountRoom);
        context.setVariable("listService", serviceList);

        context.setVariable("totalSePrice", totalSePrice);


        // Render HTML template as a string
        String htmlContent = templateEngine.process("invoice1", context);

        // Convert HTML to PDF
        invoiceService.htmlToPdf(htmlContent);

        return "redirect:/listinvoice";
    }

    @GetMapping("/invoiceDetail/{invoiceID}")
    public String invoice1(Model model, @PathVariable("invoiceID") int invoiceID) {
        Booking booking = invoiceService.getInfoInvoice(invoiceID);
        double totalSePrice = 0.0;
        double totalAmountRoom = 0.0;
        List<Service> serviceList = invoiceServiceImpl.listService(invoiceID);

        for (Service service : serviceList) {
            for (InvoiceLine invoiceLine : service.getInvoiceLine()) {
                totalSePrice += invoiceLine.getInvoiceTotalAmount();
            }
        }
        for (BookingMapping ls : booking.getBookingMapping()) {
            totalAmountRoom += ls.getBookingTotalAmount();
        }
        model.addAttribute("booking", booking);
        model.addAttribute("totalAmountRoom", totalAmountRoom);
        model.addAttribute("listService", serviceList);
        model.addAttribute("totalSePrice", totalSePrice);




        
        return "invoice1";
    }
}
