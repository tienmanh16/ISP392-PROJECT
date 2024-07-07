package com.isp.project.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.BookingRepository;
import com.isp.project.repositories.InvoiceLineRepository;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.repositories.ServiceRepository;
import com.isp.project.repositories.ServiceTypeRepository;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


// import com.itextpdf.io.source.ByteArrayOutputStream;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingMappingRepository bookingMappingRepository;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Override
    public List<Invoice> getAllInvoice() {
        return this.invoiceRepository.findAll();
    }

    @Override
    public String htmlToPdf(String processedHtml, String name) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            DefaultFontProvider fontProvider = new DefaultFontProvider(false, true, false);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(fontProvider);

            HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);
            String filePath = "D:/FPTU/" + name;
            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                byteArrayOutputStream.writeTo(outputStream);
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                // Xử lý ngoại lệ tại đây nếu cần thiết
            }
         
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Booking> testPostMan(int invoiceID) {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getInfoInvoice(int invoiceID) {
        return invoiceRepository.getReferenceById(invoiceID).getBooking();
    }

    @Override
    public List<com.isp.project.model.Service> listService(int invoiceID) {
        List<com.isp.project.model.Service> serviceList = new ArrayList<>();
        List<InvoiceLine> invoiceLines = invoiceRepository.getReferenceById(invoiceID).getInvoiceLine();

        for (InvoiceLine invoiceLine : invoiceLines) {
            serviceList.add(invoiceLine.getService());
        }

        return serviceList;
    }

    //collect quantity of service
    @Override
    public List<InvoiceLine> listInvoiceLine(int invoiceID) {
        return invoiceRepository.getReferenceById(invoiceID).getInvoiceLine();
    }
    

    

    @Override
    public double getTotalInvoiceForMonth(int month, int year) {
        double total = 0.0;
        for (Invoice ls : invoiceRepository.getInvoicesForMonth(month, year)) {
            total += ls.getTotalAmount();
        }

        return total;
    }

    @Override
    public Map<String, Double> getTotalServiceByYear(int year) {
        Map<String, Double> data = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            double totalServiceByMonth = 0;
            double totalInvoiceByMonth = 0;
            double totalBookingByMonth = 0;

            List<Invoice> invoiceList = this.invoiceRepository.getInvoicesForMonth(month, year);
            for (Invoice invoice : invoiceList) {
                totalInvoiceByMonth += invoice.getTotalAmount();
            }

            List<BookingMapping> bookingList = this.bookingMappingRepository.revenueBooking(month, year);
            for (BookingMapping booking : bookingList) {
                totalBookingByMonth += booking.getBookingTotalAmount();
            }

            totalServiceByMonth = totalInvoiceByMonth - totalBookingByMonth;

            data.put("Tháng " + month, totalServiceByMonth);
        }

        return data;
    }

    public List<Invoice> testReport(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.getReportRevenue(startDate, endDate);
    }
    // get(0).getBooking().getRegister().get(0).getEmployeeID().getFullName()

    @Override
    public Page<Invoice> pageInvoice(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        return this.invoiceRepository.findAll(pageable);
    }

    @Override
    public Page<Invoice> searchInvoice(Integer pageNo, String key) {
        Pageable pageable = PageRequest.of(pageNo - 1, 1);
        return this.invoiceRepository.searchInvoice(key, pageable);
    }

    @Override
    public Page<Invoice> searchInvoice(Integer pageNo, Date keyDate) {
        Pageable pageable = PageRequest.of(pageNo - 1, 1);
        return this.invoiceRepository.searchInvoice(keyDate, pageable);
    }

    @Override
    public void htmlToWord(String html, String filePath) {
        XWPFDocument document = new XWPFDocument();
        Document htmlDoc = Jsoup.parse(html, "UTF-8");
    
        // Xử lý phần thông tin công ty
    Elements companyInfoElements = htmlDoc.select("#company-info p");
    for (Element element : companyInfoElements) {
        XWPFParagraph companyInfoParagraph = document.createParagraph();
        XWPFRun companyInfoRun = companyInfoParagraph.createRun();
        companyInfoRun.setText(element.text());
        companyInfoParagraph.setAlignment(ParagraphAlignment.CENTER);
    }
    
        // Xử lý tiêu đề báo cáo
        String reportHeader = htmlDoc.select("#report-header").text();
        XWPFParagraph reportHeaderParagraph = document.createParagraph();
        XWPFRun reportHeaderRun = reportHeaderParagraph.createRun();
        reportHeaderRun.setBold(true);
        reportHeaderRun.setText(reportHeader);
        reportHeaderRun.setFontSize(30); 
        reportHeaderParagraph.setAlignment(ParagraphAlignment.CENTER);
        reportHeaderParagraph.setSpacingAfter(200);
    
        // Xử lý bảng dữ liệu
    Elements tables = htmlDoc.body().select("table");
    for (Element table : tables) {
        XWPFTable xwpfTable = document.createTable();
        xwpfTable.setWidth("100%");
        xwpfTable.setCellMargins(50, 50, 50, 50); // Set lề cho bảng
        Elements rows = table.select("tr");
        for (Element row : rows) {
            XWPFTableRow xwpfTableRow = xwpfTable.createRow();
            Elements cells = row.select("td, th");
            for (Element cell : cells) {
                XWPFTableCell xwpfTableCell = xwpfTableRow.createCell();
                xwpfTableCell.setText(cell.text());
            }
        }

        // Đặt khoảng cách sau bảng dữ liệu
        XWPFParagraph afterTableParagraph = document.createParagraph();
        afterTableParagraph.setSpacingAfter(200); // Đặt khoảng cách 200 twips sau bảng
    }
    
     // Xử lý phần chữ ký trong footer
     Element footerElement = htmlDoc.select("#footer").first();
     Elements signatureElements = footerElement.select("div[id^=prepared-by] p");
     
     XWPFParagraph signatureParagraph = document.createParagraph();
     signatureParagraph.setAlignment(ParagraphAlignment.CENTER);
     
     for (Element element : signatureElements) {
         XWPFRun signatureRun = signatureParagraph.createRun();
         signatureRun.setText(element.text());
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         signatureRun.addTab();
         
          // Add a tab character to separate the two signatures horizontally
     }
    
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            document.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ tại đây nếu cần thiết
        }
    }

   @Override
public List<String> seUseMost(int month, int year) {
    List<InvoiceLine> invoiceLines = invoiceLineRepository.getInvoiceLineForMonth(month, year);

    if (invoiceLines.isEmpty()) {
        return Collections.emptyList();
    }

    Map<Integer, Integer> serviceQuantityMap = new HashMap<>();
    AtomicInteger totalQuantity = new AtomicInteger(0); // Using AtomicInteger

    // Calculate total quantity
    invoiceLines.forEach(invoiceLine -> {
        int serviceId = invoiceLine.getService().getSeID();
        int quantity = invoiceLine.getQuantity();

        serviceQuantityMap.put(serviceId, serviceQuantityMap.getOrDefault(serviceId, 0) + quantity);
        totalQuantity.addAndGet(quantity); // Add quantity to AtomicInteger
    });

    // Get top 3 services by quantity and format results
    List<String> topServices = serviceQuantityMap.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
            .limit(3)
            .map(entry -> {
                int serviceId = entry.getKey();
                int quantity = entry.getValue();
                Optional<com.isp.project.model.Service> serviceOptional = serviceRepository.findById(serviceId);
                if (serviceOptional.isPresent()) {
                    com.isp.project.model.Service service = serviceOptional.get();
                    String serviceName = service.getSeName();
                    int serviceTypeId = service.getServiceType().getSeTypeID();
                    Optional<com.isp.project.model.ServiceType> serviceTypeOptional = serviceTypeRepository.findById(serviceTypeId);
                    if (serviceTypeOptional.isPresent()) {
                        String serviceTypeName = serviceTypeOptional.get().getSeTypeName();
                        
                        // Calculate percentage
                        double percentage = ((double) quantity / totalQuantity.get()) * 100;

                        return String.format("%s: %s-%.2f%%", serviceTypeName, serviceName, percentage);
                    }
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    return topServices;
}


    
    

}
