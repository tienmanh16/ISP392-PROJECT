package com.isp.project.controller;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.dto.BookingInfoDTO;
import com.isp.project.dto.BookingRoomDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Employee;
import com.isp.project.model.Register;
import com.isp.project.model.Room;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.BookingRepository;
import com.isp.project.repositories.CustomerRepository;
import com.isp.project.repositories.EmployeeRepository;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.repositories.RegisterRepository;
import com.isp.project.repositories.RoomRepository;
import com.isp.project.service.BookingService;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private BookingMappingRepository bookingMappingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoomRepository roomRepository;



    @GetMapping("/booking")
    public String BookingRoom(@RequestParam(value = "table_search", required = false) String query, Model model) {
        List<BookingRoomDTO> listBooking;
        if (query != null && !query.isEmpty()) {
            listBooking = bookingService.getAllBookingByName(query.toLowerCase());
        } else {
            listBooking = bookingService.getAllBooking();
        }
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("query", query);
        model.addAttribute("bookingInfo", new BookingInfoDTO());
        return "booking";

    }

    @GetMapping("bookingdetail")
    public String getBookingDetail(@RequestParam("id") Integer id, Model model) {
        List<BookingRoomDTO> bookingDetail = bookingService.findBookingRoomByBookingID(id);
        model.addAttribute("bookdetail", bookingDetail);
        return "bookingdetail";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable("id") Integer id) {
        try {
            boolean deleted = bookingService.deleteBookingRoom(id);
            if (deleted) {
                return ResponseEntity.ok("Booking deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete booking");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // ======================Đặt phòng ==========================
    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute("bookingInfo") BookingInfoDTO bookingInfo) {

        // Tạo và lưu thông tin của khách hàng
        Customer customer = new Customer();
        customer.setCustomerName(bookingInfo.getCustomerName());
        customer.setCustomerGender(bookingInfo.getGender());
        customer.setCustomerAddress(bookingInfo.getCustomerAddress());
        customer.setCustomerPhone(bookingInfo.getCustomerPhone());
        customer.setCustomerEmail(bookingInfo.getCustomerEmail());
        customer.setCustomerIdentificationID(bookingInfo.getCustomerIdentificationID());
        customerRepository.save(customer);

        // Tạo và lưu thông tin đặt phòng
        Booking booking = new Booking();
        booking.setCustomerID(customer);
        // Đặt ngày hiện tại cho bookingDate
        Date bookingDate = new Date(System.currentTimeMillis());
        booking.setBookingDate(bookingDate);
        booking.setCustomerQuantity(bookingInfo.getCustomerQuantity());
        booking.setIsCancelled(1); // Mặc định không hủy
        bookingRepository.save(booking);

        // Add to register
        Employee employee = employeeRepository.findById(bookingInfo.getEmployeeId());
        Register register = new Register();
        register.setEmployeeID(employee);
        register.setBookingID(booking);
        try {
            registerRepository.save(register);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Tạo và lưu thông tin ánh xạ đặt phòng
        BookingMapping bookingMapping = new BookingMapping();
        Optional<Room> roomMapping = roomRepository.findById(1);
        Room roomMapping_new = roomMapping.get();
        bookingMapping.setRoomID(roomMapping_new);
        bookingMapping.setBookingID(booking); // Lấy ID của đặt phòng mới tạo
        bookingMapping.setCheckInDate(bookingInfo.getCheckinDate());
        bookingMapping.setCheckOutDate(bookingInfo.getCheckoutDate());
        bookingMapping.setBookingTotalAmount(5000000);
        // trường phòng, vì vậy tạm thời bỏ qua
        bookingMappingRepository.save(bookingMapping);

        return "redirect:/booking"; // Chuyển hướng đến trang kết quả đặt phòng
    }

    // ========= mergeDateAndTime=================
    private Date mergeDateAndTime(Date date, String time) {
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);

        Calendar calTime = Calendar.getInstance();
        String[] timeParts = time.split(":");
        calTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        calTime.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));

        calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
        calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
        calDate.set(Calendar.SECOND, 0);

        return (Date) calDate.getTime();
    }
}
