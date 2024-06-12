package com.isp.project.service;

import java.util.List;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;

public interface RoomService {
    List<RoomDetailDTO> getAllRoomsWithDetails();

    List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id);

    List<RoomDetailDTO> getAllRoomsByStatus(String status);

    Boolean create(Room room);

    void save(Room room);

    Room findById(int id);

    void updateRoomStatus(int id, String status);

}
