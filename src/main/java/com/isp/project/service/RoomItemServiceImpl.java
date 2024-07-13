package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.model.RoomItem;
import com.isp.project.repositories.RoomItemRepository;

@Service
public class RoomItemServiceImpl implements RoomItemService {

    @Autowired
    private RoomItemRepository roomItemRepository;

    @Override
    public List<RoomItem> getAllRoomItem() {
        return roomItemRepository.findAll();
    }

    @Override
    public boolean deleteRoomItem(Integer roomItemId) {
        try {
            roomItemRepository.deleteByItemId(roomItemId);
            roomItemRepository.deleteById(roomItemId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<RoomItem> findRoomItemsByNameContaining(String keyword) {
        return roomItemRepository.findRoomItemByNameContaining(keyword);
    }

    // @Override
    // public List<RoomItem> findAll() {
    //     return roomItemRepository.findAll();
    // }
    @Override
    public List<RoomItem> findAll() {
        return roomItemRepository.findByItemsActiveTrue();
    }

    @Override
    public boolean existsByItemName(String itemName) {
        // Xử lý chuỗi để loại bỏ dấu cách và đưa về dạng lowercase
        String formattedItemName = itemName.replaceAll("\\s+", "").toLowerCase();
        return roomItemRepository.existsByItemName(formattedItemName);
    }


    @Override
    public boolean toggleRoomItemStatus(int itemId, boolean currentStatus) {
       RoomItem roomItem = roomItemRepository.findById(itemId).get();
       if(roomItem == null){
        throw new IllegalArgumentException("Item not found with ID: " + itemId);
       }
       roomItem.setItemsActive(!currentStatus);
       roomItemRepository.save(roomItem);
       return !currentStatus;
    }

}
