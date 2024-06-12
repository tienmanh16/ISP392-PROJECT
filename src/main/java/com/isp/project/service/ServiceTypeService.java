package com.isp.project.service;

import java.util.List;

import com.isp.project.model.ServiceType;

public interface ServiceTypeService {
    List<ServiceType> getAll();
    Boolean create(ServiceType serviceType);
    ServiceType findByID(Integer id);
    Boolean update(ServiceType serviceType);
    Boolean delete(Integer id);
    List<ServiceType> searchServiceType(String name);
} 