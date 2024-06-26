package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isp.project.model.GuestInformation;

public interface GuestInformationRepository extends JpaRepository<GuestInformation, Integer> {
    
}
