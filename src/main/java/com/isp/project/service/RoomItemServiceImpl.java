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

    // @Override
    // public RoomItem findByItemName(String itemName) {
    //     return roomItemRepository.findByItemName(itemName);
    // }

    @Override
    public List<RoomItem> findAll() {
        return roomItemRepository.findAll();
    }
    

}
