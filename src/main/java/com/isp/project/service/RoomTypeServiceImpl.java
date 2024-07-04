package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomTypeRepository;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

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

    @Override
    public Boolean create(RoomType roomType) {
        try {
            this.roomTypeRepository.save(roomType);
            return true;

        } catch (Exception e) {
            // Ghi log chi tiết hơn để dễ dàng chẩn đoán lỗi
            System.err.println("Failed to save RoomType: " + roomType);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<RoomType> getAll() {
        return this.roomTypeRepository.findAll();
    }

    @Override
    public RoomType findByID(Integer id) {
        return this.roomTypeRepository.findById(id).get();
    }

    @Override
    public Boolean update(RoomType roomType) {
        try {
            this.roomTypeRepository.save(roomType);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            this.roomTypeRepository.delete(findByID(id));
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    public List<RoomType> findAllActive() {
        return roomTypeRepository.findAll().stream()
            .filter(roomType -> roomType.getRoomTypeActive() == 1)
            .toList();
    }

    public List<RoomType> findAllInactive() {
        return roomTypeRepository.findAll().stream()
            .filter(roomType -> roomType.getRoomTypeActive() == 0)
            .toList();
    }

    public void updateRoomTypeActiveStatus(int id, int status) {
        RoomType roomType = roomTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("RoomType not found"));
        roomType.setRoomTypeActive(status);
        roomTypeRepository.save(roomType);
    }

    @Override
    public List<RoomType> searchRoomType(String name) {
     return this.roomTypeRepository.searchRoomType(name);   
    }

    @Override
    public List<RoomType> getAllSortedByPrice(String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by("priceHour").ascending() : Sort.by("priceHour").descending();
        return roomTypeRepository.findAll(sort);
    }

}
