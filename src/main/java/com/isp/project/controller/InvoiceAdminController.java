package com.isp.project.controller;

import java.security.Principal;
import java.sql.Date;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Email;
import com.isp.project.model.Employee;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Service;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/admin")
public class InvoiceAdminController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @Autowired

    private SpringTemplateEngine templateEngine;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    Email emailService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/listinvoice")
    public String listInvoice(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
            @Param("key") String key, @Param("keyDate") Date keyDate, Principal p) {

        //Page<Invoice> invoice = this.invoiceService.pageInvoice(pageNo);
                List<Invoice> invoice = this.invoiceService.getAllInvoice();
        // if (key != null) {
        //     invoice = this.invoiceService.searchInvoice(pageNo, key);
        //     model.addAttribute("key", key);
        // } else if (keyDate != null) {
        //     invoice = this.invoiceService.searchInvoice(pageNo, keyDate);
        //     model.addAttribute("keyDate", keyDate);
        // } else {
        //     invoice = this.invoiceService.pageInvoice(pageNo);
        // }
        // model.addAttribute("totalPage", invoice.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listInvoice", invoice);
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }

        return "listInvoice_admin";
    }

    @GetMapping("/printInvoice/{invoiceID}")
    public String printInvoice(@PathVariable("invoiceID") int invoiceID, Model model, Principal p) {
        Invoice invoice = invoiceRepository.findById(invoiceID).get();
        double totalSePrice = 0.0;
        double totalAmountRoom = 0.0;
        List<Service> serviceList = invoiceServiceImpl.listService(invoiceID);
        List<InvoiceLine> invoiceLineList = invoiceServiceImpl.listInvoiceLine(invoiceID);
        for (InvoiceLine invoiceLine : invoiceLineList) {

            totalSePrice += invoiceLine.getInvoiceTotalAmount();

        }

        totalAmountRoom = invoice.getBookingMapping().getBookingTotalAmount();

        String roomName = invoiceRepository.getReferenceById(invoiceID).getBookingMapping().getRoomID().getRoomNum();
        BookingMapping bookingMapping = invoice.getBookingMapping();

        // Prepare context for Thymeleaf
        Context context = new Context();
        context.setVariable("invoiceID", invoiceID);
        context.setVariable("totalAmountRoom", totalAmountRoom);
        context.setVariable("listService", serviceList);

        context.setVariable("totalSePrice", totalSePrice);
        context.setVariable("invoiceLineList", invoiceLineList);
        context.setVariable("roomName", roomName);
        context.setVariable("invoice", invoice);
        context.setVariable("bookingMapping", bookingMapping);

        // Render HTML template as a string
        String htmlContent = templateEngine.process("printInvoice", context);
        String name = "invoice_" + invoiceID + ".pdf";
        // Convert HTML to PDF
        invoiceService.htmlToPdf(htmlContent, name);
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }

        return "redirect:/admin/listinvoice";
    }

    @GetMapping("/invoicePayment/{invoiceID}")
    public String invoicePayment(Model model, @PathVariable("invoiceID") int invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID).get();
        double totalSePrice = 0.0;
        double totalAmountRoom = 0.0;
        List<Service> serviceList = invoiceServiceImpl.listService(invoiceID);
        List<InvoiceLine> invoiceLineList = invoiceServiceImpl.listInvoiceLine(invoiceID);
        for (InvoiceLine invoiceLine : invoiceLineList) {

            totalSePrice += invoiceLine.getInvoiceTotalAmount();

        }

        totalAmountRoom = invoice.getBookingMapping().getBookingTotalAmount();

        String roomName = invoiceRepository.getReferenceById(invoiceID).getBookingMapping().getRoomID().getRoomNum();
        BookingMapping bookingMapping = invoice.getBookingMapping();
        model.addAttribute("invoiceID", invoiceID);
        model.addAttribute("totalAmountRoom", totalAmountRoom);
        model.addAttribute("listService", serviceList);
        model.addAttribute("totalSePrice", totalSePrice);
        model.addAttribute("invoiceLineList", invoiceLineList);
        model.addAttribute("roomName", roomName);
        model.addAttribute("invoice", invoice);
        model.addAttribute("bookingMapping", bookingMapping);
          //  update total invoice after checkout
          double totalInvoice = totalAmountRoom + totalSePrice;
          Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceID);
          if (optionalInvoice.isPresent()) {
              Invoice invoiceUpdate = optionalInvoice.get();
              invoice.setTotalAmount(totalInvoice);
              invoiceRepository.save(invoiceUpdate);
          }
  
    
         String email =
         invoiceRepository.getReferenceById(invoiceID).getBooking().getCustomerID().getCustomerEmail();
         try {
         emailService.sendEmailCheckOut(email, invoiceID);
         } catch (MessagingException e) {
         // Handle the exception, e.g., log it or take appropriate action
         }
        return "invoice1_admin";
    }
    @GetMapping("/invoiceDetail/{invoiceID}")
    public String invoiceDetail(Model model, @PathVariable("invoiceID") int invoiceID) {
        Invoice invoice = invoiceRepository.findById(invoiceID).get();
        double totalSePrice = 0.0;
        double totalAmountRoom = 0.0;
        List<Service> serviceList = invoiceServiceImpl.listService(invoiceID);
        List<InvoiceLine> invoiceLineList = invoiceServiceImpl.listInvoiceLine(invoiceID);
        for (InvoiceLine invoiceLine : invoiceLineList) {

            totalSePrice += invoiceLine.getInvoiceTotalAmount();

        }

        totalAmountRoom = invoice.getBookingMapping().getBookingTotalAmount();

        String roomName = invoiceRepository.getReferenceById(invoiceID).getBookingMapping().getRoomID().getRoomNum();
        BookingMapping bookingMapping = invoice.getBookingMapping();
        model.addAttribute("invoiceID", invoiceID);
        model.addAttribute("totalAmountRoom", totalAmountRoom);
        model.addAttribute("listService", serviceList);
        model.addAttribute("totalSePrice", totalSePrice);
        model.addAttribute("invoiceLineList", invoiceLineList);
        model.addAttribute("roomName", roomName);
        model.addAttribute("invoice", invoice);
        model.addAttribute("bookingMapping", bookingMapping);

       
        return "invoice1_admin";
    }

}
