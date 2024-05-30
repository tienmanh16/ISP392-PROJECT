package com.isp.project.service;

import java.util.List;



import com.isp.project.dto.BookingRoomDTO;


public interface BookingService {
List<BookingRoomDTO> getAllBooking();
boolean deleteBookingRoom(Integer id);
List<BookingRoomDTO> findBookingRoomByBookingID(Integer bookingID);
List<BookingRoomDTO> getAllBookingByName(String customer_name);
}
