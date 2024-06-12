package com.isp.project.controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.isp.project.model.Invoice;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.BookingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;

import org.springframework.web.bind.annotation.RequestMapping;



@Controller

@RequestMapping("/admin")
public class ReportController {
    @Autowired
    private BookingMappingService bookingMappingService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @GetMapping("/report")
    public String report(Model model) {
        // khi goi den defaul thang 1 sau do se viet controller khác
        double revenueBooking = bookingMappingService.revenueBooking(5, 2024);
        int totalBooking = bookingMappingService.totalBooking(5,2024);
        double revenueService = invoiceService.getTotalInvoiceForMonth(5, 2024) - revenueBooking;
        model.addAttribute("revenueBooking", revenueBooking);
        model.addAttribute("totalBooking", totalBooking);
        model.addAttribute("revenueService", revenueService);

        Map<String, Double> data = bookingMappingService.getTotalBookingByYear(2024);
        model.addAttribute("totalBookingByYear", data.values());
       
        Map<String, Double> data1 = invoiceService.getTotalServiceByYear(2024);
        model.addAttribute("totalServiceByYear", data1.values());


        int totalCustomer = this.bookingService.getCustomerForDate(5, 2024).size();
        model.addAttribute("totalCustomer", totalCustomer);

        return "report";
    }
    @GetMapping("/getdate")
    public String reportGetdate(@RequestParam("month") int month, @RequestParam("year") int year, Model model) {
        double revenueBooking = bookingMappingService.revenueBooking(month, year);

        int totalBooking = bookingMappingService.totalBooking(month, year);

        double revenueService = invoiceService.getTotalInvoiceForMonth(month,year) - revenueBooking;
        model.addAttribute("revenueBooking", revenueBooking);
        model.addAttribute("totalBooking", totalBooking);
        model.addAttribute("revenueService", revenueService);
        
        Map<String, Double> totalBookingByYear = bookingMappingService.getTotalBookingByYear(year);
        model.addAttribute("totalBookingByYear", totalBookingByYear.values());
        model.addAttribute("test", totalBookingByYear.keySet());
        Map<String, Double> totalSerrviceByYear = invoiceService.getTotalServiceByYear(year);
        model.addAttribute("totalServiceByYear", totalSerrviceByYear.values());

        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);


        int totalCustomer = this.bookingService.getCustomerForDate(month, year).size();
        model.addAttribute("totalCustomer", totalCustomer);
        return "report";
    }

    @GetMapping("/testReport")
    public String testReport(Model model) {
        List<Invoice> ls = invoiceServiceImpl.testReport();
        model.addAttribute("ls", ls);
        return "report1.html";
    }
}
