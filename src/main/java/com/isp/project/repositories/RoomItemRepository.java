package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.isp.project.model.RoomItem;

@Repository
public interface RoomItemRepository extends JpaRepository<RoomItem, Integer> {

    @Query("SELECT r FROM RoomItem r WHERE r.ItemName LIKE CONCAT('%', :keyword, '%')")
    List<RoomItem> findRoomItemByNameContaining(@Param("keyword") String keyword);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM RoomItemMapping WHERE ItemID = :ItemID", nativeQuery = true)
    void deleteByItemId(@Param("ItemID") int ItemID);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM RoomItem r WHERE r.ItemName = :itemName")
    boolean existsByItemName(@Param("itemName") String itemName);
}
