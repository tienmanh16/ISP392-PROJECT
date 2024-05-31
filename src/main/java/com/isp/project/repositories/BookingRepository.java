package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.isp.project.model.Booking;

import jakarta.transaction.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

// List Booking
    @Query(value = "WITH NumberedBookings AS ( " +
            "    SELECT b.BookingID, c.CustomerName, bm.CheckInDate, bm.CheckOutDate, b.BookingDate, " +
            "           b.CustomerQuantity, bm.RoomID, e.Name AS employeeName, " +
            "           ROW_NUMBER() OVER (PARTITION BY b.BookingID ORDER BY bm.CheckInDate) AS RowNum " +
            "    FROM Booking b " +
            "    JOIN Customer c ON b.CustomerID = c.CustomerID " +
            "    JOIN Register rg ON b.BookingID = rg.BookingID " +
            "    JOIN Employee e ON rg.EmployeeID = e.EmployeeID " +
            "    JOIN BookingMapping bm ON b.BookingID = bm.BookingID " +
            "    JOIN Room r ON bm.RoomID = r.RoomID " +
            ") " +
            "SELECT nb.BookingID, nb.CustomerName, nb.CheckInDate, nb.CheckOutDate, nb.BookingDate, " +
            "       nb.CustomerQuantity, nb.RoomID, nb.employeeName " +
            "FROM NumberedBookings nb " +
            "WHERE nb.RowNum = 1", nativeQuery = true)
    List<Object[]> findAllBookingRoom();


    //Search Booking By Customer Name
    @Query(value = "WITH NumberedBookings AS ( " +
            "    SELECT b.BookingID, c.CustomerName, bm.CheckInDate, bm.CheckOutDate, b.BookingDate, " +
            "           b.CustomerQuantity, bm.RoomID, e.Name AS employeeName, " +
            "           ROW_NUMBER() OVER (PARTITION BY b.BookingID ORDER BY bm.CheckInDate) AS RowNum " +
            "    FROM Booking b " +
            "    JOIN Customer c ON b.CustomerID = c.CustomerID " +
            "    JOIN Register rg ON b.BookingID = rg.BookingID " +
            "    JOIN Employee e ON rg.EmployeeID = e.EmployeeID " +
            "    JOIN BookingMapping bm ON b.BookingID = bm.BookingID " +
            "    JOIN Room r ON bm.RoomID = r.RoomID " +
            "    WHERE c.CustomerName LIKE %:customerName% " +
            ") " +
            "SELECT nb.BookingID, nb.CustomerName, nb.CheckInDate, nb.CheckOutDate, nb.BookingDate, " +
            "       nb.CustomerQuantity, nb.RoomID, nb.employeeName " +
            "FROM NumberedBookings nb " +
            "WHERE nb.RowNum = 1", nativeQuery = true)
    List<Object[]> findBookingRoomByCustomerName(@Param("customerName") String customerName);


    //Search Booking Room By BookingID
    @Query(value = "SELECT b.BookingID, c.CustomerName, bm.CheckInDate, bm.CheckOutDate, b.BookingDate, b.CustomerQuantity, bm.RoomID, e.Name "
            +
            "FROM Booking b " +
            "JOIN Customer c ON b.CustomerID = c.CustomerID " +
            "JOIN BookingMapping bm ON b.BookingID = bm.BookingID " +
            "JOIN Register r ON b.BookingID = r.BookingID " +
            "JOIN Employee e ON r.EmployeeID = e.EmployeeID " +
            "WHERE b.BookingID = :bookingID", nativeQuery = true)
    List<Object[]> findBookingRoomByBookingID(@Param("bookingID") Integer bookingID);

    // Delete Booking 
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Register WHERE BookingID = :bookingID", nativeQuery = true)
    void deleteFromRegister(@Param("bookingID") Integer bookingID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM BookingMapping WHERE BookingID = :bookingID", nativeQuery = true)
    void deleteFromBookingMapping(@Param("bookingID") Integer bookingID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Booking WHERE BookingID = :bookingID", nativeQuery = true)
    void deleteFromBooking(@Param("bookingID") Integer bookingID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM BookingMapping bm " +
                   "WHERE bm.BookingID = (SELECT TOP 1 BookingID FROM Booking WHERE BookingID = :bookingID) " +
                   "AND bm.RoomID = (SELECT TOP 1 RoomID FROM BookingMapping WHERE RoomID = :roomID)", nativeQuery = true)
    void deleteByRoomIDAndBookingID(@Param("bookingID") int bookingID, @Param("roomID") int roomID);
}
