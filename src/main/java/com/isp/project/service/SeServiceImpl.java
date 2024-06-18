package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.dto.ServiceDetailDTO;
import com.isp.project.model.ServiceType;
import com.isp.project.repositories.ServiceRepository;
import com.isp.project.repositories.ServiceTypeRepository;

@Service
public class SeServiceImpl implements SeService{
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<ServiceDetailDTO> getAllServiceDetail() {
        return serviceRepository.findAllServiceDetail();
    }

    @Override
    public List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    @Override
    public List<ServiceDetailDTO> findAllServiceDetailByServiceTypeId(Integer id) {
        return serviceRepository.findAllServiceDetailByServiceTypeId(id);
    }

    @Override
    public List<ServiceDetailDTO> findAllServiceDetailBySeName(String seName) {
        return serviceRepository.findAllServiceDetailBySeName(seName);
    }

}
