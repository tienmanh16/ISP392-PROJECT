package com.isp.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.isp.project.model.Customer;
import com.isp.project.model.Employee;
import com.isp.project.repositories.CustomerRepository;
import com.isp.project.service.BookingService;
import com.isp.project.service.CustomerService;
import com.isp.project.service.EmployeeService;

@Controller
@RequestMapping("/receptionist")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/customer")
    public String listCustomer2(
            @RequestParam(value = "table_search", required = false) String queryCustomerName, Model model, Principal p) {
        List<Customer> listCustomers;
        if (queryCustomerName != null && !queryCustomerName.isEmpty()) {
            listCustomers = customerService.findCustomersByNameContaining(queryCustomerName.toLowerCase());

        } else {
            listCustomers = customerService.getAllCustomers();
        }
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
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
        return "redirect:/receptionist/customer"; // Assuming customerList is a page showing list of customers
    }

    @PostMapping("/addcustomer")
    public String addCustomer(@ModelAttribute("addCustomer") Customer addCustomer) {
        customerRepository.save(addCustomer);
        return "redirect:/receptionist/customer";
    }

    @GetMapping("/customer_check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String customerEmail) {
        boolean exists = customerService.existsByCustomerEmail(customerEmail);
        return ResponseEntity.ok(exists);
    }

}
