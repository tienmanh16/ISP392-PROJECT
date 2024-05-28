package com.isp.project.service;

import java.util.List;

import com.isp.project.model.RoomType;

public interface RoomTypeService {
    List<RoomType> getAllRoomType();
    Boolean create(RoomType roomType);
    RoomType findByID(Integer id);
    Boolean update(RoomType roomType);
    Boolean delete(Integer id);
} 