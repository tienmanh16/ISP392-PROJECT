package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import com.isp.project.model.Customer;
import com.isp.project.repositories.CustomerRepository;
import com.isp.project.service.BookingService;
import com.isp.project.service.CustomerService;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public String listCustomer2(
            @RequestParam(value = "table_search", required = false) String queryCustomerName, Model model) {
        List<Customer> listCustomers;
        if (queryCustomerName != null && !queryCustomerName.isEmpty()) {
            listCustomers = customerService.findCustomersByNameContaining(queryCustomerName.toLowerCase());

        } else {
            listCustomers = customerService.getAllCustomers();
        }
        model.addAttribute("customerList", listCustomers);
        model.addAttribute("queryCustomerName", queryCustomerName);
        model.addAttribute("addCustomer", new Customer());
        return "customer";
    }

    @GetMapping("/updateCustomer")
    public String showUpdateForm(@RequestParam("idcus") int customerId, Model model) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        model.addAttribute("customer", customer);
        return "updateCustomer";
    }

    @PostMapping("/updateCustomer")
    public String updateCustomer(@ModelAttribute("customer") Customer customer) {
        customerRepository.save(customer);
        return "redirect:/customer"; // Assuming customerList is a page showing list of customers
    }

    @PostMapping("/addcustomer")
    public String addCustomer(@ModelAttribute("addCustomer") Customer addCustomer) {
        customerRepository.save(addCustomer);
        return "redirect:/customer";
    }

}
