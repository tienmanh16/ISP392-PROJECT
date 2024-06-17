package com.isp.project.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;
import com.isp.project.model.RoomType;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>{
    @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
           + "r.id, "
           + "r.roomNum, "
           + "rt.id, "
           + "rt.name, "
           + "rt.des, "
           + "rt.priceHour, "
           + "rt.priceDay, "
           + "r.status "
           + ") "
           + "FROM Room r "
           + "JOIN r.roomType rt ")
    List<RoomDetailDTO> findAllRoomsWithDetails();

    @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
       + "r.id, "
       + "r.roomNum, "
       + "rt.id, "
       + "rt.name, "
       + "rt.des, "
       + "rt.priceHour, "
       + "rt.priceDay, "
       + "r.status "
       + ") "
       + "FROM Room r "
       + "JOIN r.roomType rt "
       + "WHERE rt.id = :roomTypeId")
    List<RoomDetailDTO> findAllRoomsWithDetailsByRoomTypeId(@Param("roomTypeId") Integer roomTypeId);

    @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
       + "r.id, "
       + "r.roomNum, "
       + "rt.id, "
       + "rt.name, "
       + "rt.des, "
       + "rt.priceHour, "
       + "rt.priceDay, "
       + "r.status "
       + ") "
       + "FROM Room r "
       + "JOIN r.roomType rt "
       + "WHERE r.status = :status")
    List<RoomDetailDTO> findAllRoomsByStatus(@Param("status") String status);

   //  @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b WHERE b.checkinDate < :checkoutDate AND b.checkoutDate > :checkinDate)")
   //  List<Room> findAvailableRooms(Date checkinDate, Date checkoutDate);

//    -- tìm phòng theo ngày----
// select * from BookingMapping
// DECLARE @checkinDate DATE = '2024-05-31';  -- Replace with your check-in date
// DECLARE @checkoutDate DATE = '2024-06-02'; -- Replace with your check-out date



// @Query(value = "SELECT r.RoomID,r.RoomNumber, rt.RoomTypeID, rt.RoomTypeName, rt.Description,rt.PricePerHour, rt.PricePerDay,r.RoomStatus" +
// "FROM Room r " +
// "INNER JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID " +
// "WHERE r.RoomID NOT IN ( " +
// "   SELECT bm.RoomID " +
// "   FROM BookingMapping bm " +
// "   WHERE bm.CheckInDate < :checkoutDate " +
// "   AND bm.CheckOutDate > :checkinDate " +
// ") AND r.RoomActive = 1", nativeQuery = true)
// List<Object[]> findAvailableRooms(@Param("checkinDate") String checkinDate,
//                    @Param("checkoutDate") String checkoutDate);

@Query(value = "SELECT r.RoomID, r.RoomNumber, rt.RoomTypeID, rt.RoomTypeName, rt.Description, rt.PricePerHour, rt.PricePerDay, r.RoomStatus " +
               "FROM Room r " +
               "INNER JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID " +
               "WHERE r.RoomID NOT IN ( " +
               "   SELECT bm.RoomID " +
               "   FROM BookingMapping bm " +
               "   WHERE bm.CheckInDate < :checkoutDate " +
               "   AND bm.CheckOutDate > :checkinDate " +
               ") AND r.RoomActive = 1", nativeQuery = true)
List<Object[]> findAvailableRooms(@Param("checkinDate") String checkinDate,
                                  @Param("checkoutDate") String checkoutDate);
}
