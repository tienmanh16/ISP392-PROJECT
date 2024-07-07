package com.isp.project.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.isp.project.dto.BookingInfoDTO;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.service.InvoiceService;
import com.isp.project.service.InvoiceServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;

public class Email {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    HttpSession session;
    @Autowired
    private TemplateEngine templateEngine;
    

     @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceServiceImpl invoiceServiceImpl;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public void sendEmail(String to, String code) throws MessagingException {
        session.setMaxInactiveInterval(5 * 60);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Code xác nhận tài khoản của bạn từ Sarine");
        Context context = new Context();
        context.setVariable("verifyCode", code);
        String htmlMsg = templateEngine.process("sendEmail", context);
        helper.setText(htmlMsg, true);

        mailSender.send(message);

    }

    public void sendEmailLeaveInfo(String to, String name ) throws MessagingException {
        session.setMaxInactiveInterval(5 * 60);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Leave information successfully!!!");
        Context context = new Context();
        context.setVariable("name", "Hello, " + name + "!!!");
        String htmlMsg = templateEngine.process("sendEmailLeaveInfo", context);
        helper.setText(htmlMsg, true);

        mailSender.send(message);

    }

    public void sendEmailBooking(String to, BookingInfoDTO bookingInfo ) throws MessagingException {
        session.setMaxInactiveInterval(5 * 60);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Booking Successfully!!!");
        Context context = new Context();
        // context.setVariable("name", "Hello, " + name + "!!!");
        context.setVariable("customerName", bookingInfo.getCustomerName());
        String htmlMsg = templateEngine.process("sendBookingEmail", context);
        helper.setText(htmlMsg, true);

        mailSender.send(message);

    }

    public void sendEmailCheckOut(String to, int invoiceID) throws MessagingException {
 
    
        // Cập nhật thời gian session
        session.setMaxInactiveInterval(5 * 60);
    
        // Tạo email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
        // Lấy thông tin booking và tính toán
        Booking booking = invoiceService.getInfoInvoice(invoiceID);
        double totalSePrice = 0.0;
        double totalAmountRoom = 0.0;
        List<Service> serviceList = invoiceServiceImpl.listService(invoiceID);
        List<InvoiceLine> invoiceLineList = invoiceServiceImpl.listInvoiceLine(invoiceID);
    
        for (InvoiceLine invoiceLine : invoiceLineList) {
            totalSePrice += invoiceLine.getInvoiceTotalAmount();
        }
    
        for (BookingMapping ls : booking.getBookingMapping()) {
            totalAmountRoom += ls.getBookingTotalAmount();
        }
    
        //double totalInvoice = totalAmountRoom + totalSePrice;
    
        // // Cập nhật tổng số tiền hóa đơn
        // Optional<Invoice> optionalInvoice = invoiceRepository.findById(invoiceID);
        // if (optionalInvoice.isPresent()) {
        //     Invoice invoice = optionalInvoice.get();
        //     invoice.setTotalAmount(totalInvoice);
        //     invoiceRepository.save(invoice);
        // }
    
        // Thiết lập các thuộc tính của email
        helper.setTo(to);
        helper.setSubject("Electronic Invoice");
    
        // Sử dụng TemplateEngine để tạo nội dung email
        Context context = new Context();
        context.setVariable("booking", booking);
        context.setVariable("totalAmountRoom", totalAmountRoom);
        context.setVariable("listService", serviceList);
        context.setVariable("totalSePrice", totalSePrice);
        context.setVariable("invoiceLineList", invoiceLineList);
    
        String htmlMsg = templateEngine.process("invoice1", context);
        helper.setText(htmlMsg, true);
    
        // Gửi email
        mailSender.send(message);
    }
    
}
