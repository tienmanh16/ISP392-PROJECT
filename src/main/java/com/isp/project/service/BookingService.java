package com.isp.project.service;

import java.util.List;
import com.isp.project.model.Booking;


public interface BookingService {

    boolean deleteBookingRoom(Integer id);

    boolean deleteBookingByRoomID(Integer id);

    List<Booking> getAllBookingByName(String customerName);

    List<Booking> getCustomerForDate(int month, int year);

    Booking getBookingByBookingID(int bookingID);

    List<Booking> getAllBookingNew();

}
