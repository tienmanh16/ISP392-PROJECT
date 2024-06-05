package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.model.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer>{

       @Query("SELECT new com.isp.project.dto.RoomTypeDetailDTO("
              + "rt.id, "
              + "rt.name, "
              + "rt.des, "
              + "rt.priceHour, "
              + "rt.priceDay, "
              + "i.id, "
              + "i.image1, "
              + "i.image2 "
              + ") "
              + "FROM ImageDetail i "
              + "JOIN i.roomType rt")
       List<RoomTypeDetailDTO> findAllRoomTypesWithDetails();


       @Query("SELECT new com.isp.project.dto.RoomTypeDetailDTO("
              + "rt.id, "
              + "rt.name, "
              + "rt.des, "
              + "rt.priceHour, "
              + "rt.priceDay, "
              + "i.id, "
              + "i.image1, "
              + "i.image2 "
              + ") "
              + "FROM ImageDetail i "
              + "JOIN i.roomType rt "
              + "WHERE rt.id = :roomTypeId")
       RoomTypeDetailDTO findRoomTypeDetailByRoomTypeId(@Param("roomTypeId") Integer roomTypeId);

}
