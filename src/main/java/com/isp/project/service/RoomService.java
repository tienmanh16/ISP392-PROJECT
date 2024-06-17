package com.isp.project.service;

import java.sql.Date;
import java.util.List;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;

public interface RoomService {
    List<RoomDetailDTO> getAllRoomsWithDetails();

    List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id);

    List<RoomDetailDTO> getAllRoomsByStatus(String status);
    
    List<RoomDetailDTO> getAvailableRooms(Date checkinDate, Date checkoutDate);
}
