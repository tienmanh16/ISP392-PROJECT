package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.dto.BookingRoomDTO;

import com.isp.project.service.BookingService;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/booking")
    public String BookingRoom(@RequestParam(value = "table_search", required = false) String query, Model model) {
        List<BookingRoomDTO> listBooking;
        if (query != null && !query.isEmpty()) {
            listBooking = bookingService.getAllBookingByName(query.toLowerCase());
                       
        }else{
            listBooking = bookingService.getAllBooking(); 
        }
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("query", query);
            return "booking";
        
    }
    
@GetMapping("bookingdetail")
    public String getBookingDetail(@RequestParam("id") Integer id, Model model) {
        List<BookingRoomDTO> bookingDetail = bookingService.findBookingRoomByBookingID(id);
            model.addAttribute("bookdetail", bookingDetail);
        return "bookingdetail";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Integer id) {
        try {
            boolean deleted = bookingService.deleteBookingRoom(id);
            if (deleted) {
                return ResponseEntity.ok("Booking deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete booking");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    
}
