
package com.isp.project.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.isp.project.model.ServiceType;
import com.isp.project.repositories.ServiceTypeRepository;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {
    private ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @Override
    public List<ServiceType> getAll() {
        return this.serviceTypeRepository.findAll();
    }

    @Override
    public Boolean create(ServiceType serviceType) {
        try {
            this.serviceTypeRepository.save(serviceType);
            return true;

        } catch (Exception e) {
            // Ghi log chi tiết hơn để dễ dàng chẩn đoán lỗi
            System.err.println("Failed to save ServiceType: " + serviceType);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ServiceType findByID(Integer id) {
        return this.serviceTypeRepository.findById(id).get();
    }

    @Override
    public Boolean update(ServiceType serviceType) {
        try {
            this.serviceTypeRepository.save(serviceType);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public Boolean delete(Integer id) {
        try {
            this.serviceTypeRepository.delete(findByID(id));
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }
    
    public List<ServiceType> findAllActive() {
        return serviceTypeRepository.findAll().stream()
            .filter(serviceType -> serviceType.getServiceTypeActive() == 1)
            .toList();
    }

    public List<ServiceType> findAllInactive() {
        return serviceTypeRepository.findAll().stream()
            .filter(serviceType -> serviceType.getServiceTypeActive() == 0)
            .toList();
    }

    public void updateServiceTypeActiveStatus(int id, int status) {
        ServiceType serviceType = serviceTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("ServiceType not found"));
        serviceType.setServiceTypeActive(status);
        serviceTypeRepository.save(serviceType);
    }

    @Override
    public List<ServiceType> searchServiceType(String name) {
       return this.serviceTypeRepository.searchServiceType(name);
    }
}
