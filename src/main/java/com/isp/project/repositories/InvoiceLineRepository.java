package com.isp.project.repositories;



import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.model.InvoiceLine;


@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Integer>{

     @Query("SELECT il FROM InvoiceLine il JOIN il.invoice i WHERE MONTH(i.InvoiceDate) = :month AND YEAR(i.InvoiceDate) = :year")
    List<InvoiceLine> getInvoiceLineForMonth(@Param("month") int month, @Param("year") int year);
}
