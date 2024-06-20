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
       @Query("UPDATE ServiceType s SET s.serviceTypeActive = :status WHERE s.SeTypeID = :SeTypeID")
       void updateServiceTypeActiveStatus(@Param("SeTypeID") int id, @Param("status") int status);

       @Query("Select c FROM ServiceType c WHERE c.SeTypeName LIKE %?1%")
       List<ServiceType> searchServiceType(String name);
}
