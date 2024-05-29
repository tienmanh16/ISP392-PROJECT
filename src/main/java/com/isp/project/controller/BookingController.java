package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.service.BookingServiceImpl;

@Controller
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingServiceImpl;


    @GetMapping("/booking")
    public String BookingRoom(Model model) {
        List<BookingRoomDTO> listBooking = bookingServiceImpl.getAllBooking();
        model.addAttribute("listBooking", listBooking);
        return "booking";
    }

    @GetMapping("/bookingdetail")
    public String themPhong() {
        return "bookingdetail";
    }

}
