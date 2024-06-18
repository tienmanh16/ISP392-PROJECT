package com.isp.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
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

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/statistic")
    public String reportGetdate(@RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model) {
        if (month == null & year == null) {
            month = 1;
            year = 2024;
        }

        double revenueBooking = bookingMappingService.revenueBooking(month, year);

        int totalBooking = bookingMappingService.totalBooking(month, year);

        double revenueService = invoiceService.getTotalInvoiceForMonth(month, year) - revenueBooking;
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


        Map<String, Double> percentByMonth =new HashMap<>();;
        percentByMonth.put("percentBooking", ((revenueBooking / (revenueService + revenueBooking)) * 100));
        percentByMonth.put("percentService",  ((revenueService / (revenueService + revenueBooking)) * 100));
        model.addAttribute("percentByMonth", percentByMonth.values());

        model.addAttribute("percentBooking", ((revenueBooking / (revenueService + revenueBooking)) * 100));
        model.addAttribute("percentService",  ((revenueService / (revenueService + revenueBooking)) * 100));
        return "report";
    }

    @GetMapping("/revenue_report")
    public String testReport(@RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model) {

        if (month == null & year == null) {
            month = 1;
            year = 2024;
        }
        double totalAmount = 0.0;
        List<Invoice> listReport = invoiceServiceImpl.testReport(month, year);
        for (Invoice ls : listReport) {
            totalAmount += ls.getTotalAmount();
        }
        model.addAttribute("ls", listReport);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("totalAmount", totalAmount);
        return "revenue";
    }

    @GetMapping("/print_revenue_report")
    public String printRevenueReport( Model model,@RequestParam(value = "month", required = false) Integer month,
    @RequestParam(value = "year", required = false) Integer year ) {

        double totalAmount = 0.0;
        List<Invoice> listReport = invoiceServiceImpl.testReport(month, year);
        for (Invoice ls : listReport) {
            totalAmount += ls.getTotalAmount();
        }
        Context context = new Context();
        context.setVariable("ls", listReport);
       
        context.setVariable("totalAmount", totalAmount);
        context.setVariable("selectedMonth", month);
        context.setVariable("selectedYear", year);

        String htmlContent = templateEngine.process("printRevenue", context);
        String name = "revenueReport_" + month + "_" + year + ".pdf";
        invoiceService.htmlToPdf(htmlContent, name);
        return "redirect:/admin/revenue_report";
    }
   
    @GetMapping("/view_report_revenue")
    public String getReport(Model model) {
        int month = 5;
        int year = 2024;
        double totalAmount = 0.0;
        List<Invoice> listReport = invoiceServiceImpl.testReport(month, year);
        for (Invoice ls : listReport) {
            totalAmount += ls.getTotalAmount();
        }
        model.addAttribute("ls", listReport);
        model.addAttribute("selectedMonth", month);
        model.addAttribute("selectedYear", year);
        model.addAttribute("totalAmount", totalAmount);
        return "printRevenue";
    }
    
@GetMapping("/testPie")
public String getMethodName() {
    return "pie";
}

}
