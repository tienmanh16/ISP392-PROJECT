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
       + "s.SeID, "
       + "s.SeName, "
       + "s.SePrice, "
       + "s.serviceActive, "
       + "st.SeTypeID, "
       + "st.SeTypeName, "
       + "st.serviceTypeActive "
       + ") "
       + "FROM Service s "
       + "JOIN s.serviceType st "
       + "WHERE s.serviceActive = 1")
    List<ServiceDetailDTO> findAllServiceDetail();

    @Query("SELECT new com.isp.project.dto.ServiceDetailDTO("
       + "s.SeID, "
       + "s.SeName, "
       + "s.SePrice, "
       + "s.serviceActive, "
       + "st.SeTypeID, "
       + "st.SeTypeName, "
       + "st.serviceTypeActive "
       + ") "
       + "FROM Service s "
       + "JOIN s.serviceType st "
       + "WHERE st.SeTypeID = :seTypeId and s.serviceActive = 1")
    List<ServiceDetailDTO> findAllServiceDetailByServiceTypeId(@Param("seTypeId") Integer seTypeId);


    @Query("SELECT new com.isp.project.dto.ServiceDetailDTO("
       + "s.SeID, "
       + "s.SeName, "
       + "s.SePrice, "
       + "s.serviceActive, "
       + "st.SeTypeID, "
       + "st.SeTypeName, "
       + "st.serviceTypeActive "
       + ") "
       + "FROM Service s "
       + "JOIN s.serviceType st "
       + "WHERE s.SeName LIKE %:seName% and s.serviceActive = 1")
    List<ServiceDetailDTO> findAllServiceDetailBySeName(@Param("seName") String seName);


    //boolean existsBySeName(String SeName);

    @Query("Select c FROM Service c WHERE c.SeName LIKE %?1%")
    List<Service> searchService(String name);

}
