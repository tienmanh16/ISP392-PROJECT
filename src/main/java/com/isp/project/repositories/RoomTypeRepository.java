package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isp.project.dto.RoomTypeDetailDTO;
import com.isp.project.model.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {

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

       @Modifying
       @Transactional
       @Query("UPDATE RoomType r SET r.roomTypeActive = :status WHERE r.id = :id")
       void updateRoomTypeActiveStatus(@Param("id") int id, @Param("status") int status);

       @Query("Select c FROM RoomType c WHERE c.name LIKE %?1%")
        List<RoomType> searchRoomType(String name);

       //List<RoomType> findAll(Sort sort);

       @Query("SELECT CASE WHEN COUNT(rt) > 0 THEN TRUE ELSE FALSE END FROM RoomType rt WHERE LOWER(REPLACE(rt.name, ' ', '')) = LOWER(:name)")
       boolean existsByRoomTypeName(@Param("name") String name);

}
