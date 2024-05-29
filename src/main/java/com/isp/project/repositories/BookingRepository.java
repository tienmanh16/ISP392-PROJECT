package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isp.project.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT b.BookingID, c.CustomerName, bm.CheckInDate, bm.CheckOutDate, b.BookingDate, b.CustomerQuantity, bm.RoomID, e.Name " +
    "FROM Booking b " +
    "JOIN Customer c ON b.CustomerID = c.CustomerID " +
    "JOIN BookingMapping bm ON b.BookingID = bm.BookingID " +
    "JOIN Register r ON b.BookingID = r.BookingID " +
    "JOIN Employee e ON r.EmployeeID = e.EmployeeID", nativeQuery = true)
List<Object[]> findAllBookingRoom();
}
