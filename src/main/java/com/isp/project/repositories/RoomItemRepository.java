package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isp.project.model.RoomItem;

@Repository
public interface RoomItemRepository extends JpaRepository<RoomItem, Integer>{

}
