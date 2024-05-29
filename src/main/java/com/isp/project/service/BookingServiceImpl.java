package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.repositories.BookingRepository;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository bookingRepository;

   
    public List<BookingRoomDTO> getAllBooking() {
        return bookingRepository.findAllBookingRoom();
    }

}
