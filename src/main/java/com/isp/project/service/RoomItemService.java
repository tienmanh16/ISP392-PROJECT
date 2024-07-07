package com.isp.project.service;

import java.util.List;
import com.isp.project.model.RoomItem;

public interface RoomItemService {
    List<RoomItem> getAllRoomItem();

    boolean deleteRoomItem(Integer id);

    List<RoomItem> findRoomItemsByNameContaining(String keyword);

    List<RoomItem> findAll();
    
    boolean existsByItemName(String ItemName);
}
