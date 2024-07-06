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
import com.isp.project.repositories.RoomRepository;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.BookingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;
import com.isp.project.service.RoomService;
import com.isp.project.service.SeService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    private RoomRepository roomRepository;
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

    //  @GetMapping("/tien")
    // public List<Room> revenueBooking() {
    //     Date date = Date.valueOf("2024-06-01");
    //     // return bookingMappingRepository.findRooms(date);
    // }


    @Autowired
    private SeService seService;

    @GetMapping("/listServices2")
    public List<Service> ServiceList2(Model model, @Param("name") String name) {
        List<Service> services;
        if (name != null ) {
            services = this.seService.searchService(name);
        } else {
            services = this.seService.findAll();
            //services = serviceRepository.findAll();
        }
        //services = serviceRepository.findAll();
        model.addAttribute("services", services);
        return services;
    }

    @GetMapping("/viet")
    public List<BookingMapping> getBookingMapping() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            // Chuyển đổi chuỗi thành java.sql.Date
            date = new Date(sdf.parse("2024-06-01").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<BookingMapping> list = bookingMappingRepository.findByCheckInDateLessThanEqual(date);
        return list;
    }
    
    
}
