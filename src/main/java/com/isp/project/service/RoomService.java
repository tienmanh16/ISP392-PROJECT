package com.isp.project.service;

import java.util.List;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;

@Service
public interface RoomService {
    List<RoomDetailDTO> getAllRoomsWithDetails();

    List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id);

    List<RoomDetailDTO> getAllRoomsByStatus(String status);

    Room findByRoomNum(String roomNum);
    
    List<Room> findAll();

}
