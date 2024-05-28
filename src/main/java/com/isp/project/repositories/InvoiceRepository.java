package com.isp.project.repositories;

import java.sql.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isp.project.dto.InvoiceDetailDTO;
import com.isp.project.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
    @Query("Select c FROM Invoice c WHERE c.CustomerName LIKE %?1%")
    List<Invoice> searchInvoice(String key);

    @Query("Select c FROM Invoice c WHERE c.InvoiceDate = ?1")
    List<Invoice> searchInvoice(Date keyDate);
    
    // @Query("SELECT new com.isp.project.dto.RoomDetailDTO(" +
    // "i.InvoiceID, " +
    // "i.TotalAmount, " +
    // "i.CustomerName, " +
    // "i.InvoiceDate, " +
    // "il.TotalAmount, " +
    // "il.Quantity, " +
    // "s.SeName, " +
    // "s.SePrice, " +
    // "bm.CheckInDate, " +
    // "bm.CheckOutDate, " +
    // "r.RoomNumber) " +
    // "FROM Invoice i " +
    // "JOIN InvoiceLine il ON i.InvoiceID = il.InvoiceID " +
    // "JOIN Service s ON il.SeID = s.SeID " +
    // "JOIN Booking b ON i.BookingID = b.BookingID " +
    // "JOIN BookingMapping bm ON b.BookingID = bm.BookingID " +
    // "JOIN Room r ON bm.RoomID = r.RoomID " +
    // "WHERE i.InvoiceID = :invoiceId " +
    // "ORDER BY i.InvoiceID")
    // List<InvoiceDetailDTO> findInvoiceDetail(@Param("invoiceId") int invoiceId);
}