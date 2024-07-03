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

    @Override
    public List<RoomItem> findAll() {
        return roomItemRepository.findAll();
    }

    // @Override
    // public boolean existsByItemName(String ItemName) {
    // return roomItemRepository.existsByItemName(ItemName);
    // }

}
