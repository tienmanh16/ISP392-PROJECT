package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>{
    @Query("SELECT new com.isp.project.dto.RoomDetailDTO("
           + "r.id, "
           + "r.roomNum, "
           + "rt.id, "
           + "rt.name, "
           + "rt.des, "
           + "rt.priceHour, "
           + "rt.priceDay, "
           + "r.status, "
           + "i.id, "
           + "i.image1, "
           + "i.image2 "
           + ") "
           + "FROM Room r "
           + "JOIN r.roomType rt "
           + "JOIN rt.imageDetail i")
    List<RoomDetailDTO> findAllRoomsWithDetails();

}
