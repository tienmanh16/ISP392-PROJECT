package com.isp.project.repositories;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.isp.project.model.Booking;
import com.isp.project.model.Invoice;

@Service
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT b FROM Invoice b ORDER BY b.InvoiceID DESC")
    List<Invoice> findAllOrderInvoiceIDDesc();


    @Query("Select c FROM Invoice c WHERE c.CustomerName LIKE %?1%")
    Page<Invoice> searchInvoice(String key, Pageable pageable);

    @Query("Select c FROM Invoice c WHERE c.InvoiceDate = ?1")
    Page<Invoice> searchInvoice(Date keyDate, Pageable pageable);

    @Query("SELECT i FROM Invoice i JOIN i.booking b WHERE MONTH(i.InvoiceDate) = :month AND YEAR(i.InvoiceDate) = :year and b.isCancelled = 0")
    List<Invoice> getInvoicesForMonth(@Param("month") int month, @Param("year") int year);

    // @Query("SELECT i FROM Invoice i JOIN i.booking b WHERE MONTH(i.InvoiceDate) =
    // :month AND YEAR(i.InvoiceDate) = :year")
    // List<Invoice> getReportRevenue(@Param("month") int month, @Param("year") int
    // year);

    @Query("SELECT i FROM Invoice i JOIN i.booking b WHERE i.InvoiceDate BETWEEN :startDate AND :endDate and b.isCancelled = 0")
    List<Invoice> getReportRevenue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}