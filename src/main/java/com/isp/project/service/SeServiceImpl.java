package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public List<com.isp.project.model.Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public Boolean create(com.isp.project.model.Service service) {
        try {
            this.serviceRepository.save(service);
            return true;

        } catch (Exception e) {
            // Ghi log chi tiết hơn để dễ dàng chẩn đoán lỗi
            System.err.println("Failed to save Room: " + service);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void save(com.isp.project.model.Service service) {
        serviceRepository.save(service);
    }

    @Override
    public com.isp.project.model.Service findBySeId(int id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    public void updateServiceActiveStatus(int id, int status) {
        com.isp.project.model.Service service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        service.setServiceActive(status);
        serviceRepository.save(service);
    }


    // @Override
    // public boolean existsByServiceName(String seName) {
    //     return serviceRepository.existsBySeName(seName);
    // }

    @Override
    public List<com.isp.project.model.Service> searchService(String name) {
        return this.serviceRepository.searchService(name);
    }
    @Override
    public boolean existsBySeName(String seName) {
        String trimmedSeName = seName.replaceAll("\\s+", "").toLowerCase();
        return serviceRepository.existsBySeName(trimmedSeName);
    }




}
