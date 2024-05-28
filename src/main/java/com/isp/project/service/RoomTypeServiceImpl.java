package com.isp.project.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomTypeRepository;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{
@Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> getAllRoomType() {
     return this.roomTypeRepository.findAll();
    }
    @Override
    public Boolean create(RoomType roomType) {
        try {
            this.roomTypeRepository.save(roomType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
        return false;
    }
    @Override
    public Boolean delete(Integer id) {
        try {
            this.roomTypeRepository.delete(findByID(id));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
}
