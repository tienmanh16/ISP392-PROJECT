package com.isp.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isp.project.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(value = "SELECT * FROM Customer WHERE CustomerName LIKE %:keyword%", nativeQuery = true)
    List<Customer> findCustomersByNameContaining(@Param("keyword") String keyword);

}
