package com.isp.project.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.isp.project.model.Invoice;
@Service
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
    @Query("Select c FROM Invoice c WHERE c.CustomerName LIKE %?1%")
    List<Invoice> searchInvoice(String key);

    @Query("Select c FROM Invoice c WHERE c.InvoiceDate = ?1")
    List<Invoice> searchInvoice(Date keyDate);
    
    @Query("SELECT i FROM Invoice i JOIN i.booking b WHERE MONTH(b.bookingDate) = :month AND YEAR(b.bookingDate) = :year")
    List<Invoice> getInvoicesForMonth(@Param("month") int month, @Param("year") int year);
}