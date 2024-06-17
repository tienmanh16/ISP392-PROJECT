package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isp.project.model.RoomItemMapping;

@Service
public interface ItemDetailService {
    List<RoomItemMapping> findAll();

    RoomItemMapping findById(int id);

    void deleteById(Integer id);

    RoomItemMapping save(RoomItemMapping entity);

}
