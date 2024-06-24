package com.isp.project.service;

import java.util.List;

import com.isp.project.model.Customer;

public interface CustomerService {

    List<Customer> getAllCustomers();

    boolean deleteCustomers(Integer id);

    List<Customer> findCustomersByNameContaining(String keyword);
}
