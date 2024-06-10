package com.isp.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isp.project.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

}
