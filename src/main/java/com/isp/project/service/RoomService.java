package com.isp.project.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;

@Service
public interface RoomService {
    //List<RoomDetailDTO> getAllRoomsWithDetails();

    Page<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id, Integer pageNo);

    List<RoomDetailDTO> getAllRoomsByStatus(String status);
    
    List<RoomDetailDTO> getAvailableRooms(Date checkinDate, Date checkoutDate);
    // Room findByRoomNum(String roomNum);
    
    List<Room> findAll();
    RoomCustomerDTO getAllRoomCusWithDetailsByRoomId(Integer id);

    Page<RoomDetailDTO> getAll(Integer pageNo);

    List<RoomDetailDTO> getRoomFromFilter(String status, Integer id);

    void updateRoomStatusByRoomId(Integer roomId);

    
}
