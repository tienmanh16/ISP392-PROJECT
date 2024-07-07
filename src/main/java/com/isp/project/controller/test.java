package com.isp.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.isp.project.dto.RoomDTO;
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
import com.isp.project.model.RoomType;
import com.isp.project.model.Service;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.BookingRepository;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.repositories.RoomRepository;
import com.isp.project.repositories.RoomTypeRepository;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.BookingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;
import com.isp.project.service.RoomService;
import com.isp.project.service.RoomTypeService;
import com.isp.project.service.SeService;

import jakarta.mail.MessagingException;

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
import org.springframework.web.bind.annotation.RequestParam;





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
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private BookingMappingService bookingMappingService; 
    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping("/{id}")
    public List<Booking> getMethodName(@PathVariable int id ) {
        return invoiceService.testPostMan(id);
    }

    @GetMapping("/tien1")
    public List<InvoiceLine> getMethodName12() {
 
        return invoiceService.listInvoiceLine(16);
        
    }
    
    @GetMapping("/viet")
public Booking getMethodName() {
    // Retrieve the booking by ID
    Booking updateBooking = bookingRepository.findByBookingID(9);

    // Ensure the booking and its invoice list are not null
    if (updateBooking != null && updateBooking.getInvoice() != null && !updateBooking.getInvoice().isEmpty()) {
        // Retrieve the first invoice
        Invoice firstInvoice = updateBooking.getInvoice().get(0);

        // Update the total amount
        double totalAmount = firstInvoice.getTotalAmount();
        totalAmount += 100;
        firstInvoice.setTotalAmount(totalAmount);

        // Save the updated booking
        bookingRepository.save(updateBooking);
    }

    return updateBooking;
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

     @GetMapping("/tien")
    public Booking revenueBooking() {
        return bookingMappingRepository.getReferenceById(9).getBookingID();
    }


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
    
    @GetMapping("/testRoom")
    public List<String> getMethodName(
    Model model) {
        List<String> ls = this.roomTypeService.rateUseRoomTypeByMonth(9, 2024);
        return ls;
    }
    // @GetMapping("/invoice")
    // public List<Invoice> getMethodName1(Model model) {
    //     // send mail
    //     String email = "tiennmhe172825@fpt.edu.vn";
    //     int invoiceID = ;
    //     try {
    //         emailService.sendEmailCheckOut(email, invoiceID);
    //     } catch (MessagingException e) {
    //         // Handle the exception, e.g., log it or take appropriate action
    //     }
    // }
    
    
    
}
