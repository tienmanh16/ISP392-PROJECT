package com.isp.project.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.lang.Double;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.BookingMapping;
import com.isp.project.repositories.BookingMappingRepository;

@Service
public class BookingMappingServiceImpl implements BookingMappingService {
    @Autowired
    private BookingMappingRepository bookingMappingRepository;

    @Override
    public Double revenueBooking(int month, int year) {
        List<BookingMapping> ls = this.bookingMappingRepository.revenueBooking(month, year);
        double totalAmountBooking = 0;
        if (ls == null) {
            return 0.0;
        } else {
            for (BookingMapping bookingMapping : ls) {
                totalAmountBooking += bookingMapping.getBookingTotalAmount();
            }
        }
        return totalAmountBooking;
    }

    @Override
    public int totalBooking(int month, int year) {
        return this.bookingMappingRepository.revenueBooking(month, year).size();
    }

    @Override
    public Map<String, Double> getTotalBookingByYear(int year) {
        Map<String, Double> data = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            double totalBookingByMonth = 0;

            List<BookingMapping> ls = this.bookingMappingRepository.revenueBooking(month, year);

            for (BookingMapping booking : ls) {
                totalBookingByMonth += booking.getBookingTotalAmount();
            }

            data.put("Tháng " + month, totalBookingByMonth);
        }

        return data;
    }

    // public List<BookingMapping> getAvailableRooms(String checkinDate) {
    //     if (checkinDate == null || checkinDate.isEmpty()) {
    //         return bookingMappingRepository.findAll();
    //     } else {
    //         Date date = Date.valueOf(checkinDate);
    //         return bookingMappingRepository.findByCheckInDateLessThanEqual(date);
    //     }
    // }

    public List<BookingMapping> getAvailableRooms(Date checkinDate) {
        if (checkinDate == null) {
            return bookingMappingRepository.findAll();
        } else {
            return bookingMappingRepository.findByCheckInDateLessThanEqual(checkinDate);
        }
    }

}
