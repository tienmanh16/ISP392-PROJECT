package com.isp.project.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.lang.Double;
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
    
        // Lặp qua tất cả các tháng trong năm
        for (int month = 1; month <= 12; month++) {
            double totalBookingByMonth = 0; // Đặt lại tổng tiền cho mỗi tháng
    
            // Lấy danh sách booking từ repository cho tháng và năm cụ thể
            List<BookingMapping> ls = this.bookingMappingRepository.revenueBooking(month, year);
            
            // Tính tổng giá trị của tất cả các booking trong danh sách
            for (BookingMapping booking : ls) {
                totalBookingByMonth += booking.getBookingTotalAmount();
            }
            
            // Đặt giá trị tổng vào map với key là tháng
            data.put("Tháng " + month, totalBookingByMonth);
        }
        
        return data;
    }
    
}
