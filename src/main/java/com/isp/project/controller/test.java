package com.isp.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Employee;
import com.isp.project.model.Employee;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Register;
import com.isp.project.model.Room;
import com.isp.project.model.Service;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.BookingRepository;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.BookingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;
import com.isp.project.service.RoomService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//class này test postman thui

@RestController
@RequestMapping(path = "/api/test")

public class test {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingMappingRepository bookingMappingRepository;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private InvoiceServiceImpl invoiceService;

    @Autowired
    private BookingMappingService bookingMappingService;

    // @GetMapping("/{id}")
    // public List<Booking> getMethodName(@PathVariable int id ) {
    // return bookingMappingRepository.testPostMan(id);
    // }

    @GetMapping("/viet")
    public void getMethodName() {
        Room room = new Room();
        room.setId(2);
        Booking booking = new Booking();
        booking.setBookingID(16);
        bookingRepository.deleteByRoomAndBooking(booking, room);
    }
    // @GetMapping("/tien")
    // public Employee revenueBooking() {
    // return invoiceService.testReport();
    // }

}
