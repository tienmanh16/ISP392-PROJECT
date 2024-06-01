package com.isp.project.service;

import java.util.List;

import com.isp.project.dto.RoomTypeDetailDTO;

public interface RoomTypeService {
    List<RoomTypeDetailDTO> getAllRoomTypesWithDetails();

    RoomTypeDetailDTO getRoomTypeDetailById(Integer id);

}
