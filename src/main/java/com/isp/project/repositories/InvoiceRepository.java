package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isp.project.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
    
}
