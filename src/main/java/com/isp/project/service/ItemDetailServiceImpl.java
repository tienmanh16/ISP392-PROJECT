package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.RoomItemMapping;
import com.isp.project.repositories.ItemDetailRepository;

@Service
public class ItemDetailServiceImpl implements ItemDetailService{
    
    @Autowired
    private ItemDetailRepository itemDetailRepository;

    @Override
    public List<RoomItemMapping> findAll() {
        return itemDetailRepository.findAll();
    }

     @Override
    public RoomItemMapping save(RoomItemMapping entity) {
        return itemDetailRepository.save(entity);
    }

    @Override
    public void deleteById(Integer id) {
         itemDetailRepository.deleteById(id);
    }

    @Override
    public RoomItemMapping findById(int id) {
        return itemDetailRepository.findById(id);
    }

    @Override
    public boolean isDuplicate(RoomItemMapping entity) {
        RoomItemMapping existingMapping = itemDetailRepository.findByRoomAndItem(
            entity.getRoom().getId(), entity.getRoomItem().getItemID());

        return existingMapping != null;
    }

  

   

}
