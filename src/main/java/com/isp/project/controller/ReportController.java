package com.isp.project.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.springframework.ui.Model;

import com.isp.project.model.Invoice;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.BookingService;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;
import com.isp.project.service.RoomTypeService;

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
    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("/statistic")
    public String reportGetdate(@RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "year", required = false) Integer year,
            Model model) {
        if (month == null & year == null) {
            month = 7;
            year = 2024;
        }

        double revenueBooking = bookingMappingService.revenueBooking(month, year);

        int totalBooking = bookingService.totalBooking(month, year);

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

        Map<String, Double> percentByMonth = new HashMap<>();
        
        percentByMonth.put("percentBooking", ((revenueBooking / (revenueService + revenueBooking)) * 100));
        percentByMonth.put("percentService", ((revenueService / (revenueService + revenueBooking)) * 100));
        model.addAttribute("percentByMonth", percentByMonth.values());

        model.addAttribute("percentBooking", ((revenueBooking / (revenueService + revenueBooking)) * 100));
        model.addAttribute("percentService", ((revenueService / (revenueService + revenueBooking)) * 100));


        List<String> useMostService = invoiceService.seUseMost(month, year);
        model.addAttribute("useMostService", useMostService);


        List<String> rateUseRoomType = roomTypeService.rateUseRoomTypeByMonth(month,year);
        model.addAttribute("rateUseRoomType", rateUseRoomType);
        return "report";
    }

    @GetMapping("/revenue_report")
    public String testReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        if (startDate == null && endDate == null) {
            startDate = LocalDate.of(2024, 1, 1); // Set default start date
            endDate = LocalDate.of(2024, 12, 31); // Set default end date
        }

        double totalAmount = 0.0;
        List<Invoice> listReport = invoiceServiceImpl.testReport(startDate, endDate);
        for (Invoice ls : listReport) {
            totalAmount += ls.getTotalAmount();
        }
        model.addAttribute("ls", listReport);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("totalAmount", totalAmount);
        return "revenue";
    }

    @GetMapping("/print_revenue_report")
    public String printRevenueReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        double totalAmount = 0.0;
        List<Invoice> listReport = invoiceServiceImpl.testReport(startDate, endDate);
        for (Invoice ls : listReport) {
            totalAmount += ls.getTotalAmount();
        }
        Context context = new Context();
        context.setVariable("ls", listReport);

        context.setVariable("totalAmount", totalAmount);
        context.setVariable("startDate", startDate);
        context.setVariable("endDate", endDate);

        String htmlContent = templateEngine.process("printRevenuePDF", context);
        String name = "revenueReport_" + startDate + "_" + endDate + ".pdf";
        invoiceService.htmlToPdf(htmlContent, name);
        return "redirect:/admin/revenue_report";
    }

    @GetMapping("/view_report_revenue")
    public String getReport(Model model) {
    int month = 5;
    int year = 2024;
    double totalAmount = 0.0;
    LocalDate startDate = LocalDate.of(year, month, 1);
    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
    List<Invoice> listReport = invoiceServiceImpl.testReport(startDate, endDate);
    for (Invoice ls : listReport) {
    totalAmount += ls.getTotalAmount();
    }
    model.addAttribute("ls", listReport);
    model.addAttribute("selectedMonth", month);
    model.addAttribute("selectedYear", year);
    model.addAttribute("totalAmount", totalAmount);
    return "printRevenue";
    }

@GetMapping("/print_revenue_report_word")
public ResponseEntity<byte[]> printRevenueReportWord(
        @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate)
        throws IOException {

    if (startDate == null && endDate == null) {
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 12, 31);
    }

    double totalAmount = 0.0;
    List<Invoice> listReport = invoiceServiceImpl.testReport(startDate, endDate);
    for (Invoice ls : listReport) {
        totalAmount += ls.getTotalAmount();
    }
    Context context = new Context();
    context.setVariable("ls", listReport);
    context.setVariable("totalAmount", totalAmount);
    context.setVariable("startDate", startDate);
    context.setVariable("endDate", endDate);

    String htmlContent = templateEngine.process("printRevenue", context);
    String fileName = "revenueReport_" + startDate + "_" + endDate + ".docx";
    String filePath = "D:/FPTU/" + fileName;
    invoiceService.htmlToWord(htmlContent, filePath);

    try (InputStream wordStream = new FileInputStream(filePath)) {
        byte[] wordBytes = wordStream.readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);

        return ResponseEntity.ok()
                .headers(headers)
                .body(wordBytes);
    } catch (IOException e) {
        e.printStackTrace();
        // Xử lý ngoại lệ tại đây nếu cần thiết
        throw e;
    }
}

    @GetMapping("/testPie")
    public String getMethodName() {
        return "pie";
    }

}
