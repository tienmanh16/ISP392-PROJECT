package com.isp.project.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.isp.project.model.BookingMapping;

public interface BookingMappingService {
    Double revenueBooking(int month, int year);

    int totalBooking(int month, int year);

    Map<String, Double> getTotalBookingByYear(int year);
    //  public List<BookingMapping> findAvailableRooms(Date checkinDate);
    // public List<BookingMapping> getAvailableRooms(String checkinDate);
    public List<BookingMapping> getAvailableRooms(Date checkinDate);
}
