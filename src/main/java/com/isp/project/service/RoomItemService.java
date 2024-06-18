package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.model.RoomItem;

@Service
public interface RoomItemService {
//  RoomItem findByItemName(String itemName);

    List<RoomItem> findAll();

}

