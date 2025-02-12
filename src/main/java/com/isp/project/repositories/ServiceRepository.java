package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.dto.ServiceDetailDTO;
import com.isp.project.model.Room;
import com.isp.project.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Query("SELECT new com.isp.project.dto.ServiceDetailDTO("
       + "s.seID, "
       + "s.seName, "
       + "s.sePrice, "
       + "s.serviceActive, "
       + "st.seTypeID, "
       + "st.seTypeName, "
       + "st.serviceTypeActive "
       + ") "
       + "FROM Service s "
       + "JOIN s.serviceType st "
       + "WHERE s.serviceActive = 1")
    List<ServiceDetailDTO> findAllServiceDetail();

    @Query("SELECT new com.isp.project.dto.ServiceDetailDTO("
       + "s.seID, "
       + "s.seName, "
       + "s.sePrice, "
       + "s.serviceActive, "
       + "st.seTypeID, "
       + "st.seTypeName, "
       + "st.serviceTypeActive "
       + ") "
       + "FROM Service s "
       + "JOIN s.serviceType st "
       + "WHERE st.seTypeID = :seTypeId and s.serviceActive = 1")
    List<ServiceDetailDTO> findAllServiceDetailByServiceTypeId(@Param("seTypeId") Integer seTypeId);


    @Query("SELECT new com.isp.project.dto.ServiceDetailDTO("
       + "s.seID, "
       + "s.seName, "
       + "s.sePrice, "
       + "s.serviceActive, "
       + "st.seTypeID, "
       + "st.seTypeName, "
       + "st.serviceTypeActive "
       + ") "
       + "FROM Service s "
       + "JOIN s.serviceType st "
       + "WHERE s.seName LIKE %:seName% and s.serviceActive = 1")
    List<ServiceDetailDTO> findAllServiceDetailBySeName(@Param("seName") String seName);
    
    @Query("SELECT c FROM Service c WHERE LOWER(REPLACE(c.seName, ' ', '')) LIKE LOWER(CONCAT('%', REPLACE(?1, ' ', ''), '%'))")
    List<Service> searchService(String name);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Service s WHERE LOWER(REPLACE(s.seName, ' ', '')) = LOWER(:seName)")
    boolean existsBySeName(@Param("seName") String seName);

}
