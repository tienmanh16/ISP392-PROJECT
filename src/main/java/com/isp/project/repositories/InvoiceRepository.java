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
    
    @Query(value = "SELECT \r\n" + //
                "    i.InvoiceID,\r\n" + //
                "    i.TotalAmount AS InvoiceTotalAmount,\r\n" + //
                "    i.CustomerName,\r\n" + //
                "    i.InvoiceDate,\r\n" + //
                "    il.TotalAmount AS LineTotalAmount,\r\n" + //
                "    il.Quantity,\r\n" + //
                "    s.SeName,\r\n" + //
                "    s.SePrice,\r\n" + //
                "    bm.CheckInDate,\r\n" + //
                "    bm.CheckOutDate,\r\n" + //
                "    r.RoomNumber\r\n" + //
                "FROM \r\n" + //
                "    Invoice i\r\n" + //
                "JOIN \r\n" + //
                "    InvoiceLine il ON i.InvoiceID = il.InvoiceID\r\n" + //
                "JOIN \r\n" + //
                "    [Service] s ON il.SeID = s.SeID\r\n" + //
                "JOIN \r\n" + //
                "    Booking b ON i.BookingID = b.BookingID\r\n" + //
                "JOIN \r\n" + //
                "    BookingMapping bm ON b.BookingID = bm.BookingID\r\n" + //
                "JOIN \r\n" + //
                "    Room r ON bm.RoomID = r.RoomID\r\n" + //
                "WHERE \r\n" + //
                "    i.InvoiceID = :invoiceID\r\n" + //
                "ORDER BY \r\n" + //
                "    i.InvoiceID;", nativeQuery = true)
    List<Object[]> findInvoiceDetail(@Param("invoiceID") int invoiceID);
}