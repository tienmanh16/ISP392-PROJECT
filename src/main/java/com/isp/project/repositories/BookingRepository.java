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

    //===============================Search Booking By Customer Name =========================================
    @Query("SELECT b FROM Booking b JOIN b.customerID c WHERE c.customerName LIKE %:name%")
    List<Booking> findBookingByCustomerName(@Param("name") String name);

    //==================Search Booking Room By BookingID========================= 
    @Query("SELECT b FROM Booking b WHERE b.bookingID = :bookingID")
    Booking findByBookingID(@Param("bookingID") int bookingID);

    //====================================Delete Booking======================================= 
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
    @Query("DELETE FROM BookingMapping b WHERE b.roomID = :roomID")
    void deleteByRoomID(@Param("roomID") Integer roomID);


    @Query("SELECT i FROM Booking i RIGHT JOIN i.customerID b WHERE MONTH(i.bookingDate) = :month AND YEAR(i.bookingDate) = :year")
    List<Booking> getCustomerForDate(@Param("month") int month, @Param("year") int year);


    //=========================================================================================

    

}