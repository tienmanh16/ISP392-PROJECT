package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "SELECT new com.isp.project.dto.BookingRoomDTO( "
            + "b.booking_id, "
            + "c.customer_name, "
            + "bm.check_in_date, "
            + "bm.check_out_date, "
            + "b.booking_date, "
            + "b.customer_quantity, "
            + "bm.room_id, "
            + "e.name) "
            + "FROM booking b "
            + "JOIN customer c ON b.customer_id = c.customer_id "
            + "JOIN booking_mapping bm ON bm.booking_id = b.booking_id "
            + "JOIN register r ON r.booking_id = b.booking_id "
            + "JOIN employee e ON e.employee_id = r.employee_id", nativeQuery = true)
            List<BookingRoomDTO> findAllBookingRoom();

}
