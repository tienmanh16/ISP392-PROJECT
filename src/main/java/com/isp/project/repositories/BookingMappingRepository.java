package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.isp.project.model.BookingMapping;

@Repository
public interface BookingMappingRepository extends JpaRepository<BookingMapping, Integer> {

    @Query("SELECT bm FROM BookingMapping bm join bm.bookingID b WHERE MONTH(bm.checkInDate) = :month AND YEAR(bm.checkInDate) = :year and b.isCancelled = 0")
    List<BookingMapping> revenueBooking(@Param("month") int month, @Param("year") int year);

}
