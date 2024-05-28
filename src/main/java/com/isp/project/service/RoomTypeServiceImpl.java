package com.isp.project.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomTypeRepository;


public class RoomTypeServiceImpl implements RoomTypeService{
@Autowired
    private RoomTypeRepository roomTypeRepository;
    @Override
    public List<RoomType> getAllRoomType() {
     return this.roomTypeRepository.findAll();
    }
    
}
