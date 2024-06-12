package com.isp.project.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isp.project.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
    @Query("Select c FROM Invoice c WHERE c.CustomerName LIKE %?1%")
    List<Invoice> searchInvoice(String key);

    @Query("Select c FROM Invoice c WHERE c.InvoiceDate = ?1")
    List<Invoice> searchInvoice(Date keyDate);
    
}