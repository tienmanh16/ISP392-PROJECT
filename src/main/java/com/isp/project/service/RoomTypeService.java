package com.isp.project.service;

import java.sql.Date;
import java.util.List;

import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.model.Invoice;
import com.isp.project.model.RoomType;

public interface RoomTypeService {
    List<RoomTypeDetailDTO> getAllRoomTypesWithDetails();

    RoomTypeDetailDTO getRoomTypeDetailById(Integer id);

    List<RoomType> getAll();
    Boolean create(RoomType roomType);
    RoomType findByID(Integer id);
    Boolean update(RoomType roomType);
    Boolean delete(Integer id);

    List<RoomType> findByName(String name);
    List<RoomType> searchRoomType(String name);
}
