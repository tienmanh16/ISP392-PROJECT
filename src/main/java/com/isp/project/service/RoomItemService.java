package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.model.RoomItem;


import java.util.List;

import com.isp.project.model.Customer;
import com.isp.project.model.RoomItem;


public interface RoomItemService {
List<RoomItem> getAllRoomItem();
boolean deleteRoomItem(Integer id);
List<RoomItem> findRoomItemsByNameContaining(String keyword);
List<RoomItem> findAll();
// boolean existsByItemName(String ItemName);
}

