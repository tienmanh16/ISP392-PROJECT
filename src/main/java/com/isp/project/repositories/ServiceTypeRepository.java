package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isp.project.model.ServiceType;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Integer>{

       @Modifying
       @Transactional
       @Query("UPDATE ServiceType s SET s.serviceTypeActive = :status WHERE s.seTypeID = :seTypeID")
       void updateServiceTypeActiveStatus(@Param("seTypeID") int id, @Param("status") int status);

       @Query("SELECT c FROM ServiceType c WHERE LOWER(REPLACE(c.seTypeName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(?1, ' ', ''), '%'))")
       List<ServiceType> searchServiceType(String name);

       @Query("SELECT CASE WHEN COUNT(st) > 0 THEN TRUE ELSE FALSE END FROM ServiceType st WHERE LOWER(REPLACE(st.seTypeName, ' ', '')) = LOWER(:seTypeName)")
       boolean existsBySeTypeName(@Param("seTypeName") String seTypeName);


}
