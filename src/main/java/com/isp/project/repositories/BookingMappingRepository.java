package com.isp.project.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.model.BookingMapping;

@Repository
public interface BookingMappingRepository extends JpaRepository<BookingMapping, Integer> {
    
    @Query("SELECT b FROM BookingMapping b WHERE MONTH(b.checkInDate) = :month AND YEAR(b.checkInDate) = :year")
    List<BookingMapping> revenueBooking(@Param("month") int month,  @Param("year") int year);
}
