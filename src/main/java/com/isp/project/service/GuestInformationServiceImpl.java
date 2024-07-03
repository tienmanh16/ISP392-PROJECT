package com.isp.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isp.project.model.GuestInformation;
import com.isp.project.repositories.GuestInformationRepository;

@Service
public class GuestInformationServiceImpl implements GuestInformationService {
    @Autowired
    private GuestInformationRepository guestInformationRepository;

    @Override
    public List<GuestInformation> getAll() {
        return this.guestInformationRepository.findAll();

    }

    @Override
    public Boolean create(GuestInformation guestInformation) {
        try {
            this.guestInformationRepository.save(guestInformation);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean contactStatus(int id, boolean currentStatus) {
        GuestInformation guestInformation = this.guestInformationRepository.findById(id).get();
        if (guestInformation == null) {
            throw new IllegalArgumentException("Guest Information not found with ID: " + id);
        }
        guestInformation.setContact(!currentStatus);
        this.guestInformationRepository.save(guestInformation);
        return !currentStatus;
    }

    @Override
    public Page<GuestInformation> pageInvoice(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        return this.guestInformationRepository.findAll(pageable);
    }

}
