package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;
import com.isp.project.repositories.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsWithDetails() {
        return roomRepository.findAllRoomsWithDetails();
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsWithDetailsByRoomTypeId(Integer id) {
        return roomRepository.findAllRoomsWithDetailsByRoomTypeId(id);
    }

    @Override
    public List<RoomDetailDTO> getAllRoomsByStatus(String status) {
        return roomRepository.findAllRoomsByStatus(status);
    }

    @Override
    public Boolean create(Room room) {
        try {
            this.roomRepository.save(room);
            return true;

        } catch (Exception e) {
            // Ghi log chi tiết hơn để dễ dàng chẩn đoán lỗi
            System.err.println("Failed to save Room: " + room);
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public void updateRoomStatus(int id, String status) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            room.setStatus(status);
            roomRepository.save(room);
        }
    }

}
