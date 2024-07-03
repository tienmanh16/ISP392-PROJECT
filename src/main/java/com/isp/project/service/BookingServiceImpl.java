package com.isp.project.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.isp.project.model.Booking;
import com.isp.project.model.Room;
import com.isp.project.repositories.BookingRepository;

@Service

public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Booking> getAllBookingNew() {
        return bookingRepository.findAll();
    }

    @Override
    public boolean deleteBookingRoom(Integer bookingID) {
        try {

            bookingRepository.deleteFromRegister(bookingID);
            bookingRepository.deleteFromBookingMapping(bookingID);
            bookingRepository.deleteById(bookingID);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteBookingMappingByRoomAndBooking(Booking booking, Room room) {
        try {
            bookingRepository.deleteByRoomAndBooking(booking, room);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Booking> getCustomerForDate(int month, int year) {
        return this.bookingRepository.getCustomerForDate(month, year);
    }

    @Override
    public Booking getBookingByBookingID(int bookingID) {
        return bookingRepository.findByBookingID(bookingID);

    }

    @Override
    public List<Booking> getAllBookingByName(String customerName) {
        return bookingRepository.findBookingByCustomerName(customerName);
    }

    @Override
    public int totalBooking(int month, int year) {
        return this.bookingRepository.totalBooking(month, year).size();
    }

}
