package com.isp.project.service;


import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isp.project.dto.InvoiceDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Register;
import com.isp.project.repositories.InvoiceRepository;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;


// import com.itextpdf.io.source.ByteArrayOutputStream;

@Service
public class InvoiceServiceImpl implements InvoiceService{
@Autowired
    private InvoiceRepository invoiceRepository;
    @Override
    public List<Invoice> getAllInvoice() {
     return this.invoiceRepository.findAll();
    }
    @Override
    public List<Invoice> searchInvoice(String key) {
     return this.invoiceRepository.searchInvoice(key);
    }
    @Override
    public List<Invoice> searchInvoice(Date keyDate) {
        return this.invoiceRepository.searchInvoice(keyDate);
    }
    // @Override
    // public List<InvoiceDetailDTO> findInvoiceDetail(int InvoiceID) {
    //     List<Object[]> rawResults = invoiceRepository.findInvoiceDetail(InvoiceID); // Ensure you are using the
    //                                                                                        // correct method name
    //     List<InvoiceDetailDTO> invoiceDetailDTOs = new ArrayList<>();
    //     for (Object[] rawResult : rawResults) {
    //         // Extracting data from the rawResult array and casting it to the appropriate
    //         // types
    //         int invoiceID = ((Number) rawResult[0]).intValue(); // InvoiceID
    //         double invoiceTotalAmount = ((Number) rawResult[1]).doubleValue(); // InvoiceTotalAmount
    //         String customerName = (String) rawResult[2]; // CustomerName
    //         Date invoiceDate = (Date) rawResult[3]; // InvoiceDate
    //         double lineTotalAmount = ((Number) rawResult[4]).doubleValue(); // LineTotalAmount
    //         int quantity = ((Number) rawResult[5]).intValue(); // Quantity
    //         String seName = (String) rawResult[6]; // SeName
    //         double sePrice = ((Number) rawResult[7]).doubleValue(); // SePrice
    //         Date checkInDate = (Date) rawResult[8]; // CheckInDate
    //         Date checkOutDate = (Date) rawResult[9]; // CheckOutDate
    //         String roomNumber = ((String) rawResult[10]);
    //         // Create a new InvoiceDetailDTO with the extracted data
    //         InvoiceDetailDTO dto = new InvoiceDetailDTO(invoiceID, invoiceTotalAmount, customerName, invoiceDate, lineTotalAmount, quantity, seName, sePrice, checkInDate, checkOutDate,roomNumber);
    //         invoiceDetailDTOs.add(dto);
    //     }
    
    //     return invoiceDetailDTOs;
    // }
    @Override
    public String htmlToPdf(String processedHtml) {
       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

       try {
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            DefaultFontProvider fontProvider = new DefaultFontProvider(false, true, false);
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setFontProvider(fontProvider);

            HtmlConverter.convertToPdf(processedHtml, pdfWriter, converterProperties);    
            FileOutputStream fout = new FileOutputStream("D:/FPTU/Invoice.pdf");
            byteArrayOutputStream.writeTo(fout);
            byteArrayOutputStream.close();
            byteArrayOutputStream.flush();
            fout.close();
           return null;
            
       }catch (Exception e) {
            e.printStackTrace();
       }


        return null;
    }
   
    public List<InvoiceLine> testPostMan(int id) {
        return invoiceRepository.getReferenceById(id).getInvoiceLine();
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
    
   
}

