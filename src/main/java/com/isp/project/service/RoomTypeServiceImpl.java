package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.repositories.RoomTypeRepository;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{
    private final RoomTypeRepository roomTypeRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository) {
        this.roomTypeRepository = roomTypeRepository;
    }

    @Override
    public List<RoomTypeDetailDTO> getAllRoomTypesWithDetails() {
        return roomTypeRepository.findAllRoomTypesWithDetails();
    }

    @Override
    public RoomTypeDetailDTO getRoomTypeDetailById(Integer id) {
        return roomTypeRepository.findRoomTypeDetailByRoomTypeId(id); 
    }


}
