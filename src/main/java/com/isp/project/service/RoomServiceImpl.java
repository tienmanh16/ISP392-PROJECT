package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;
import com.isp.project.repositories.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public Room findByRoomNum(String roomNum) {
        return roomRepository.findByRoomNum(roomNum);
    }

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // @Override
    // public List<RoomDetailDTO> getAllRoomsWithDetails() {
    //     return roomRepository.findAllRoomsWithDetails();
    // }

    @Override
    public List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id) {
        return roomRepository.findAllRoomsWithDetailsByRoomTypeId(id);
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsByStatus(String status) {
        return roomRepository.findAllRoomsByStatus(status);
    }

    
}
