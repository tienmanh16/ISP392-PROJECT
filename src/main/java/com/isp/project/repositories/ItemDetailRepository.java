package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isp.project.model.RoomItemMapping;

@Repository
public interface ItemDetailRepository extends JpaRepository<RoomItemMapping, Integer>{
 RoomItemMapping findById(int id);
}
