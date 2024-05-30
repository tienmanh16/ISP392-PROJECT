package com.isp.project.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.repositories.BookingRepository;

@Service

public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<BookingRoomDTO> getAllBooking() {
        List<Object[]> rawResults = bookingRepository.findAllBookingRoom(); // Ensure you are using the correct method
                                                                            // name
        List<BookingRoomDTO> bookingRoomDTOs = new ArrayList<>();

        for (Object[] rawResult : rawResults) {
            // Extracting data from the rawResult array and casting it to the appropriate
            // types
            Integer bookingId = ((Number) rawResult[0]).intValue(); // booking_id
            String customerName = (String) rawResult[1]; // customer_name
            Date checkInDate = (Date) rawResult[2]; // check_in_date
            Date checkOutDate = (Date) rawResult[3]; // check_out_date
            Date bookingDate = (Date) rawResult[4]; // booking_date
            Integer customerQuantity = ((Number) rawResult[5]).intValue(); // customer_quantity
            Integer roomId = ((Number) rawResult[6]).intValue(); // room_id
            String employeeName = (String) rawResult[7]; // employee name (assuming e.name is employee name)

            // Create a new BookingRoomDTO with the extracted data
            BookingRoomDTO dto = new BookingRoomDTO(bookingId, customerName, checkInDate, checkOutDate, bookingDate,
                    customerQuantity, roomId, employeeName);
            bookingRoomDTOs.add(dto);
        }

        return bookingRoomDTOs;
    }

    @Override
    public boolean deleteBookingRoom(Integer id) {
        try {
            bookingRepository.deleteFromRegister(id);
            bookingRepository.deleteFromBookingMapping(id);
            bookingRepository.deleteFromBooking(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BookingRoomDTO> getAllBookingByName(String name) {
        List<Object[]> rawResults = bookingRepository.findBookingRoomByCustomerName(name); // Ensure you are using the
                                                                                           // correct method name
        List<BookingRoomDTO> bookingRoomDTOs = new ArrayList<>();
        for (Object[] rawResult : rawResults) {
            // Extracting data from the rawResult array and casting it to the appropriate
            // types
            Integer bookingId = ((Number) rawResult[0]).intValue(); // booking_id
            String customerName = (String) rawResult[1]; // customer_name
            Date checkInDate = (Date) rawResult[2]; // check_in_date
            Date checkOutDate = (Date) rawResult[3]; // check_out_date
            Date bookingDate = (Date) rawResult[4]; // booking_date
            Integer customerQuantity = ((Number) rawResult[5]).intValue(); // customer_quantity
            Integer roomId = ((Number) rawResult[6]).intValue(); // room_id
            String employeeName = (String) rawResult[7]; // employee name (assuming e.name is employee name)

            // Create a new BookingRoomDTO with the extracted data
            BookingRoomDTO dto = new BookingRoomDTO(bookingId, customerName, checkInDate, checkOutDate, bookingDate,
                    customerQuantity, roomId, employeeName);
            bookingRoomDTOs.add(dto);
        }

        return bookingRoomDTOs;
    }

    @Override
    public List<BookingRoomDTO> findBookingRoomByBookingID(Integer bookingID) {
        List<Object[]> rawResults = bookingRepository.findBookingRoomByBookingID(bookingID);
        List<BookingRoomDTO> findbyID = new ArrayList<>();
        for (Object[] rawResult : rawResults) {
            // Extracting data from the rawResult array and casting it to the appropriate
            // types
            Integer bookingId = ((Number) rawResult[0]).intValue(); // booking_id
            String customerName = (String) rawResult[1]; // customer_name
            Date checkInDate = (Date) rawResult[2]; // check_in_date
            Date checkOutDate = (Date) rawResult[3]; // check_out_date
            Date bookingDate = (Date) rawResult[4]; // booking_date
            Integer customerQuantity = ((Number) rawResult[5]).intValue(); // customer_quantity
            Integer roomId = ((Number) rawResult[6]).intValue(); // room_id
            String employeeName = (String) rawResult[7]; // employee name (assuming e.name is employee name)

            // Create a new BookingRoomDTO with the extracted data
            BookingRoomDTO dto = new BookingRoomDTO(bookingId, customerName, checkInDate, checkOutDate, bookingDate,
                    customerQuantity, roomId, employeeName);
                    findbyID.add(dto);
        }

        return findbyID;
    }
}
