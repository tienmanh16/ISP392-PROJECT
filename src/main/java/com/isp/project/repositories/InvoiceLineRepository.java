package com.isp.project.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.model.InvoiceLine;

import jakarta.transaction.Transactional;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Integer>{

    // @Transactional
    // @Modifying
    // @Query("INSERT INTO InvoiceLine (InvoiceTotalAmount, SeID, Quantity, InvoiceID) " +
    //        "VALUES (:amount, :seId, :quantity, :invoiceId)")
    // void insertInvoiceLine(@Param("amount") Double amount,
    //             @Param("seId") Integer seId,
    //             @Param("quantity") Integer quantity,
    //             @Param("invoiceId") Integer invoiceId);

}
