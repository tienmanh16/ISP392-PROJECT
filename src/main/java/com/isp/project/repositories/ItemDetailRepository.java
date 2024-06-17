package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.model.RoomItemMapping;

@Repository
public interface ItemDetailRepository extends JpaRepository<RoomItemMapping, Integer>{
 RoomItemMapping findById(int id);
 @Query("SELECT r FROM RoomItemMapping r WHERE r.room.id = :roomId AND r.roomItem.id = :itemId")
    RoomItemMapping findByRoomAndItem(@Param("roomId") int roomId, @Param("itemId") int itemId);
}
