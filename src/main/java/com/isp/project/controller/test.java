package com.isp.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Employee;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Register;
import com.isp.project.model.RoomItem;
import com.isp.project.model.Service;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.BookingRepository;
import com.isp.project.repositories.CustomerRepository;
import com.isp.project.repositories.EmployeeRepository;
import com.isp.project.repositories.RoomItemRepository;
import com.isp.project.service.BookingService;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;
import com.isp.project.service.RoomService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    private EmployeeService employeeService;

    @Autowired 
    EmployeeRepository employeeRepository;

    @Autowired 
    RoomService roomService;

    @Autowired 
    RoomItemRepository roomItemRepository;

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

    @PostMapping("/roomtest")
    public List<RoomItem> testPostMan() {
        // return roomService.getAvailableRooms("2024-05-31", "2024-06-02");
    return roomItemRepository.findAll();
    }

}
