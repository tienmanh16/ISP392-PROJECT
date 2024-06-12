package com.isp.project.service;

import java.util.List;

import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.model.Booking;


public interface BookingService {
List<BookingRoomDTO> getAllBooking();
boolean deleteBookingRoom(Integer id);
boolean deleteBookingByRoomID(Integer id);
List<BookingRoomDTO> findBookingRoomByBookingID(Integer bookingID);
List<BookingRoomDTO> getAllBookingByName(String customer_name);
List<Booking> getCustomerForDate(int month, int year);
    
}
