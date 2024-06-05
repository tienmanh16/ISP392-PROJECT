package com.isp.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Register;
import com.isp.project.model.Service;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.BookingRepository;
import com.isp.project.service.BookingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//class này test postman thui

@RestController
@RequestMapping(path ="/api/test")

public class test {
  
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired 
    private BookingMappingRepository bookingMappingRepository;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<BookingMapping> deleteBooking(@PathVariable Integer id) {
        Booking booking = bookingRepository.getReferenceById(id);
        Integer bookingId = booking.getBookingID();
        BookingMapping bookingMapping = bookingMappingRepository.findById(bookingId).orElse(null);
        if (bookingMapping != null) {
            bookingService.testPostMan(id);
            return ResponseEntity.ok(bookingMapping);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    
}
