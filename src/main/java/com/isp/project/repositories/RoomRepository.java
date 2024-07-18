package com.isp.project.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.dto.RoomInvoiceDTO;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
      @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
                  + "r.id, "
                  + "r.roomNum, "
                  + "rt.id, "
                  + "rt.name, "
                  + "rt.des, "
                  + "rt.priceHour, "
                  + "rt.priceDay, "
                  + "r.status, "
                  + "r.cleaning "
                  + ") "
                  + "FROM Room r "
                  + "JOIN r.roomType rt ")
      Page<RoomDetailDTO> findAllRoomsWithDetails(Pageable pageable);

   @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
         + "r.id, "
         + "r.roomNum, "
         + "rt.id, "
         + "rt.name, "
         + "rt.des, "
         + "rt.priceHour, "
         + "rt.priceDay, "
         + "r.status, "
         + "r.cleaning "
         + ") "
         + "FROM Room r "
         + "JOIN r.roomType rt ")
   List<RoomDetailDTO> findAllRoomsWithDetails1();


    @Query("SELECT new com.isp.project.dto.RoomDTO("
           + "r.id, "
           + "r.roomNum, "
           + "rt.name, "
           + "r.cleaning, "
           + "bm.bookingMappingID, "
           + "bm.bookingMappingActive "
           + ") "
           + "FROM BookingMapping bm "
           + "JOIN bm.roomID r "
           + "JOIN r.roomType rt ")
    List<RoomDTO> findAllRooms();
    
    @Query("SELECT new com.isp.project.dto.RoomDTO("
           + "r.id, "
           + "r.roomNum, "
           + "rt.name, "
           + "r.cleaning, "
           + "bm.bookingMappingID, "
           + "bm.bookingMappingActive "
           + ") "
           + "FROM BookingMapping bm "
           + "JOIN bm.roomID r "
           + "JOIN r.roomType rt "
           + "WHERE bm.checkInDate <= :checkInDate")
    List<RoomDTO> findAllRoomsWithCheckInDate(@Param("checkInDate") Date checkinDate);

    @Query("SELECT new com.isp.project.dto.RoomInvoiceDTO("
           + "bm.bookingMappingID, "
           + "r.id, "
           + "r.roomNum, "
           + "rt.name, "
           + "r.cleaning, "
           + "bm.bookingMappingActive, "
           + "i.InvoiceID "
           + ") "
           + "FROM Invoice i "
           + "LEFT JOIN i.bookingMapping bm "
           + "JOIN bm.roomID r "
           + "JOIN r.roomType rt "
           + "WHERE bm.bookingMappingID = :bookingMappingID")
    RoomInvoiceDTO findInvoiceIdByBookingMappingId(@Param("bookingMappingID") Integer bookingMappingId);

      @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
                  + "r.id, "
                  + "r.roomNum, "
                  + "rt.id, "
                  + "rt.name, "
                  + "rt.des, "
                  + "rt.priceHour, "
                  + "rt.priceDay, "
                  + "r.status, "
                  + "r.cleaning "
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
                  + "r.status, "
                  + "r.cleaning "
                  + ") "
                  + "FROM Room r "
                  + "JOIN r.roomType rt "
                  + "WHERE rt.id = :roomTypeId")
      Page<RoomDetailDTO> findAllRoomsWithDetailsByRoomTypeId(@Param("roomTypeId") Integer roomTypeId,
                  Pageable pageable);

      @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
                  + "r.id, "
                  + "r.roomNum, "
                  + "rt.id, "
                  + "rt.name, "
                  + "rt.des, "
                  + "rt.priceHour, "
                  + "rt.priceDay, "
                  + "r.status, "
                  + "r.cleaning "
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
                  + "r.status, "
                  + "r.cleaning "
                  + ") "
                  + "FROM Room r "
                  + "JOIN r.roomType rt "
                  + "WHERE r.status = :status")
      List<RoomDetailDTO> findAllRoomsByStatus(@Param("status") String status);

      @Query("SELECT new com.isp.project.dto.RoomCustomerDTO("
                  + "bm.bookingMappingID, "
                  + "bm.checkInDate, "
                  + "bm.checkOutDate, "
                  + "bm.bookingTotalAmount, "
                  + "r.roomNum, "
                  + "r.status, "
                  + "b.bookingDate, "
                  + "b.customerQuantity, "
                  + "c.customerName, "
                  + "c.customerAddress, "
                  + "c.customerPhone, "
                  + "c.customerEmail, "
                  + "c.customerIdentificationID, "
                  + "c.customerGender "
                  + ") "
                  + "FROM BookingMapping bm "
                  + "JOIN bm.roomID r "
                  + "JOIN bm.bookingID b "
                  + "JOIN b.customerID c "
                  + "WHERE r.id = :roomId")
      RoomCustomerDTO findAllRoomCusWithDetailsByRoomId(@Param("roomId") Integer roomId);

      Page<Room> findAll(Pageable pageable);

      // @Modifying
      // @Query("UPDATE Room SET status = 'Rented Room' WHERE id = :roomId")
      // void updateRoomStatusByRoomId(@Param("roomId") Integer roomId);
      @Modifying
      @Query("UPDATE BookingMapping SET bookingMappingActive = 2 WHERE bookingMappingID = :bookingMappingId")
      void updateRoomStatusByRoomId(@Param("bookingMappingId") Integer bookingMappingId);

   @Modifying
   @Query("UPDATE Room SET cleaning = 'Not CLean' WHERE id = :roomId")
   void updateRoomStatusByRoomId2(@Param("roomId") Integer roomId);

      @Modifying
      @Query("UPDATE Room SET cleaning = :cleaning WHERE id = :roomId")
      void updateRoomCleaningByRoomId(@Param("roomId") Integer roomId, @Param("cleaning") String cleaning);

      @Modifying
      @Query("UPDATE BookingMapping SET bookingMappingActive = 0 WHERE bookingMappingID = :bookingMappingId")
      void updateBookingMappingActive(@Param("bookingMappingId") Integer bookingMappingId);

      @Query(value = "SELECT r.RoomID, r.RoomNumber, rt.RoomTypeID, rt.RoomTypeName, rt.Description, rt.PricePerHour, rt.PricePerDay, r.RoomStatus, r.cleaning "
                  +
                  "FROM Room r " +
                  "INNER JOIN RoomType rt ON r.RoomTypeID = rt.RoomTypeID " +
                  "WHERE r.RoomID NOT IN ( " +
                  "   SELECT bm.RoomID " +
                  "   FROM BookingMapping bm " +
                  "   WHERE bm.CheckInDate < :checkoutDate " +
                  "   AND bm.CheckOutDate > :checkinDate " +
                  "   AND bm.BookingMappingActive != 0 " +
                  ") AND r.RoomActive = 1", nativeQuery = true)
      List<Object[]> findAvailableRooms(@Param("checkinDate") Date checkinDate,
                  @Param("checkoutDate") Date checkoutDate);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Room r WHERE LOWER(REPLACE(r.roomNum, ' ', '')) = LOWER(:roomNum)")
    boolean existsByRoomNum(@Param("roomNum") String roomNum);


    @Query("SELECT c FROM Room c WHERE REPLACE(c.roomNum, ' ', '') LIKE CONCAT('%', REPLACE(?1, ' ', ''), '%')")
    List<Room> searchRoom(String roomNum);


}
