package com.isp.project.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.isp.project.dto.ServiceDetailDTO;
import com.isp.project.model.ServiceType;

public interface SeService {
    List<ServiceDetailDTO> getAllServiceDetail();

    List<ServiceType> getAllServiceTypes();

    List<ServiceDetailDTO> findAllServiceDetailByServiceTypeId(Integer id);

    List<ServiceDetailDTO> findAllServiceDetailBySeName(@Param("seName") String seName);

}
