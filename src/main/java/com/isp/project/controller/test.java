package com.isp.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Employee;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Register;
import com.isp.project.model.Service;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




//class này test postman thui

@RestController
@RequestMapping(path ="/api/test")

public class test {
    @Autowired
    private InvoiceServiceImpl invoiceService;

    
    @Autowired
    private BookingMappingService bookingMappingService; 

    @GetMapping("/{id}")
    public List<Booking> getMethodName(@PathVariable int id ) {
        return invoiceService.testPostMan(id);
    }


    // @GetMapping("/tien")
    // public Employee revenueBooking() {
    //     return invoiceService.testReport();
    // }
    
    
}
