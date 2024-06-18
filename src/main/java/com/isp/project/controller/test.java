package com.isp.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Register;
import com.isp.project.model.Room;
import com.isp.project.model.Service;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;
import com.isp.project.service.RoomServiceImpl;
import com.isp.project.service.RoomTypeServiceImpl;

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
    private RoomServiceImpl roomServiceImpl;

    @Autowired
    private RoomTypeServiceImpl roomTypeServiceImpl;

    @GetMapping("/{id}")
    public List<InvoiceLine> getMethodName(@PathVariable int id ) {
        return invoiceService.testPostMan(id);
    }

    @GetMapping("/loc/{id}")
    public Customer getMethodName1(@PathVariable int id ) {
        return roomServiceImpl.test(id);
    }


    @GetMapping("/loc2/{id}")
    public Room getMethodName2(@PathVariable int id ) {
        return roomServiceImpl.testR(id);
    }

    @GetMapping("/loc3/{id}")
    public RoomCustomerDTO getMethodName3Room(@PathVariable int id ) {
        return roomServiceImpl.getAllRoomCusWithDetailsByRoomId(id);
    }




    
}
