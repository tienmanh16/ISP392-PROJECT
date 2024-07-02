package com.isp.project.service;

import java.sql.Date;
import java.util.List;

import com.isp.project.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;

@Service
public interface RoomService {
    List<RoomDetailDTO> getAllRoomsWithDetails();

    Page<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id, Integer pageNo);

    List<RoomDetailDTO> getAllRoomsByStatus(String status);

    List<RoomDetailDTO> getAvailableRooms(Date checkinDate, Date checkoutDate);
    // Room findByRoomNum(String roomNum);

    List<Room> findAll();
    RoomCustomerDTO getAllRoomCusWithDetailsByRoomId(Integer id);

    Boolean create(Room room);

    void save(Room room);

    Room findById(int id);

    void updateRoomActiveStatus(int id, int status);

    Page<RoomDetailDTO> getAll(Integer pageNo);

    List<RoomDetailDTO> getRoomFromFilter(String status, Integer id);

    void updateRoomStatusByRoomId(Integer roomId);
    boolean existsByRoomNum(String roomNum);
    List<Room> searchRoom(String name);

    void updateRoomStatusByRoomId2(Integer roomId);

    void updateBookingMappingActive(Integer bookingMappingId);

    void updateRoomCleaningByRoomId(Integer roomId, String cleaning);


}
