package com.isp.project.service;

import java.util.List;

import com.isp.project.dto.RoomDetailDTO;

public interface RoomService {
    List<RoomDetailDTO> getAllRoomsWithDetails();

    List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id);

    List<RoomDetailDTO> getAllRoomsByStatus(String status);

}
