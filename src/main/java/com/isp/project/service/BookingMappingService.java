package com.isp.project.service;

import java.util.Map;

public interface BookingMappingService {
    Double revenueBooking(int month, int year);
    int totalBooking(int month, int year);
    Map<String, Double> getTotalBookingByYear(int year);
}
