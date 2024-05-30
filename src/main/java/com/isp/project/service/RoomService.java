package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.repositories.RoomRepository;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomDetailDTO> getAllRoomsWithDetails() {
        return roomRepository.findAllRoomsWithDetails();
    }

    public List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id){
        return roomRepository.findAllRoomsWithDetailsByRoomTypeId(id);

    }

    public List<RoomDetailDTO> getAllRoomsByStatus(String status){
        return roomRepository.findAllRoomsByStatus(status);

    }

}
