package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.isp.project.model.BookingMapping;

@Repository
public interface BookingMappingRepository extends JpaRepository<BookingMapping, Integer>{

}
