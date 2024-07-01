package com.isp.project.service;

import java.util.List;
import com.isp.project.model.Booking;
import com.isp.project.model.Room;

public interface BookingService {

    boolean deleteBookingRoom(Integer id);

    boolean deleteBookingMappingByRoomAndBooking(Booking booking, Room room);

    List<Booking> getAllBookingByName(String customerName);

    List<Booking> getCustomerForDate(int month, int year);

    Booking getBookingByBookingID(int bookingID);

    List<Booking> getAllBookingNew();

}
