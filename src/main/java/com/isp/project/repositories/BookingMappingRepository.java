package com.isp.project.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Room;


@Repository
public interface BookingMappingRepository extends JpaRepository<BookingMapping, Integer> {

    @Query("SELECT b FROM BookingMapping b WHERE MONTH(b.checkInDate) = :month AND YEAR(b.checkInDate) = :year")
    List<BookingMapping> revenueBooking(@Param("month") int month, @Param("year") int year);

     List<BookingMapping> findByCheckInDateLessThanEqual(Date checkInDate);

     @Query("SELECT bm FROM BookingMapping bm JOIN FETCH bm.roomID r JOIN FETCH r.roomType WHERE bm.checkInDate <= :checkinDate")
     List<BookingMapping> findByCheckInDateWithRoomAndRoomType(@Param("checkinDate") Date checkinDate);

    

}
