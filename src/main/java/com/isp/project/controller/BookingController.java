package com.isp.project.controller;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isp.project.dto.BookingInfoDTO;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Booking;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Email;
import com.isp.project.model.Employee;
import com.isp.project.model.Invoice;
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
import com.isp.project.service.EmployeeService;

@Controller
// =========================
@RequestMapping("/receptionist")
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

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    Email emailService;

    // ============================== GET ALL BOOKING
    // ================================================================================
    @GetMapping("/booking")
    public String BookingRoom(@RequestParam(value = "table_search", required = false) String query, Model model,
            Principal p) {
        List<Booking> listBooking;
        if (query != null && !query.isEmpty()) {
            listBooking = bookingService.getAllBookingByName(query);
        } else {
            listBooking = bookingService.getAllBookingNew();
        }
        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                BookingInfoDTO bookingInfo = new BookingInfoDTO();
                bookingInfo.setEmployeeId(user.getId());
                model.addAttribute("user1", user);
                model.addAttribute("bookingInfo", bookingInfo);
            } else {
            }
        }
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("query", query);
        return "booking";
    }

    // ================================== Booking Detail
    // =========================================================================
    @GetMapping("bookingdetail")
    public String getBookingDetail(@RequestParam("id") Integer id, Model model) {
        Booking bookingDetail = bookingService.getBookingByBookingID(id);
        model.addAttribute("bookdetail", bookingDetail);
        model.addAttribute("newBookingMapping", new BookingInfoDTO());
        return "bookingdetail";

    }

    // ============================= Delete Booking
    // ===============================================================================
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

    // ====================== Đặt phòng
    // ============================================================================================
    @PostMapping("/saveBooking")
    public String saveBooking(@ModelAttribute("bookingInfo") BookingInfoDTO bookingInfo) {

        Optional<Customer> existingCustomer = customerRepository
                .findByCustomerIdentificationID(bookingInfo.getCustomerIdentificationID());

        Customer customer;
        if (existingCustomer.isPresent()) {
            customer = existingCustomer.get();
        } else {
            customer = new Customer();
            customer.setCustomerName(bookingInfo.getCustomerName());
            customer.setCustomerGender(bookingInfo.getGender());
            customer.setCustomerAddress(bookingInfo.getCustomerAddress());
            customer.setCustomerPhone(bookingInfo.getCustomerPhone());
            customer.setCustomerEmail(bookingInfo.getCustomerEmail());
            customer.setCustomerIdentificationID(bookingInfo.getCustomerIdentificationID());
        }
        customerRepository.save(customer);

        // Create and save booking information
        Booking booking = new Booking();
        booking.setCustomerID(customer);
        Date bookingDate = new Date(System.currentTimeMillis());
        booking.setBookingDate(bookingDate);
        booking.setCustomerQuantity(bookingInfo.getCustomerQuantity());
        // booking.setIsCancelled(0); // Default to not cancelled
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

        double total_room = 0;
        // Parse selectedRoomsJson to List<RoomDetailDTO>
        List<RoomDetailDTO> selectedRooms = convertJsonToRoomDetailDTOList(bookingInfo.getSelectedRoomsJson());
        for (RoomDetailDTO roomDetail : selectedRooms) {
            Room room = roomRepository.findById(roomDetail.getId()).orElse(null);
            if (room != null) {
                total_room += room.getRoomType().getPriceDay();
                BookingMapping bookingMapping = new BookingMapping();
                bookingMapping.setBookingID(booking);
                bookingMapping.setRoomID(room);
                bookingMapping.setBookingMappingActive(1);
                bookingMapping.setCheckInDate(bookingInfo.getCheckinDate());
                bookingMapping.setCheckOutDate(bookingInfo.getCheckoutDate());
                bookingMapping.setBookingTotalAmount(roomDetail.getPriceDay()); // Set appropriate amount

                bookingMappingRepository.save(bookingMapping);
            }
        }

        // create Invoice
        // Invoice newInvoice = new Invoice();
        // newInvoice.setBooking(booking);
        // newInvoice.setCustomerName(customer.getCustomerName());
        // newInvoice.setTotalAmount(total_room);
        // newInvoice.setInvoiceDate(bookingInfo.getCheckoutDate());
        // invoiceRepository.save(newInvoice);

        // =================SendEmail=====================================
        String emailCustomer = customer.getCustomerEmail();
        try {
            emailService.sendEmailBooking(emailCustomer, bookingInfo);
        } catch (Exception e) {
            // TODO: handle exception
        }

        // return "redirect:/booking"; // Redirect to booking result page
        return "redirect:/receptionist/booking";
    }

    @PostMapping("/saveBookingMapping")
    public String saveBookingMapping(@RequestParam Date checkinDate,
            @RequestParam Date checkoutDate,
            @RequestParam int bookingIdMapping,
            @RequestParam String selectedRoomsJson) {
        List<RoomDetailDTO> selectedRooms = convertJsonToRoomDetailDTOList(selectedRoomsJson);
        Booking updateBooking = bookingService.getBookingByBookingID(bookingIdMapping);
    
        for (RoomDetailDTO roomDetail : selectedRooms) {
            Room room = roomRepository.findById(roomDetail.getId()).orElse(null);
            if (room != null) {
                BookingMapping bookingMapping = new BookingMapping();
                bookingMapping.setBookingID(updateBooking);
                bookingMapping.setRoomID(room);
                bookingMapping.setBookingMappingActive(1);
                bookingMapping.setCheckInDate(checkinDate);
                bookingMapping.setCheckOutDate(checkoutDate);
                bookingMapping.setBookingTotalAmount(roomDetail.getPriceDay()); // Set appropriate amount
                bookingMappingRepository.save(bookingMapping);
            }
        }
        return "redirect:/receptionist/bookingdetail?id=" + updateBooking.getBookingID();
    }

    private List<RoomDetailDTO> convertJsonToRoomDetailDTOList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<RoomDetailDTO>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @DeleteMapping("/bookingMappings")
    public ResponseEntity<String> deleteBookingMappingsByRoomAndBooking(@RequestParam Integer roomId,
            @RequestParam Integer bookingId) {
        Optional<Room> room = roomRepository.findById(roomId);
        Room delete_room = room.get();
        Booking updateBooking = bookingService.getBookingByBookingID(bookingId);
        double total_room_update = updateBooking.getInvoice().get(0).getTotalAmount();

        try {
            total_room_update = total_room_update - delete_room.getRoomType().getPriceDay();
            updateBooking.getInvoice().get(0).setTotalAmount(total_room_update);
            bookingRepository.save(updateBooking);
            boolean deleted = bookingService.deleteBookingMappingByRoomAndBooking(updateBooking, delete_room);
            if (deleted) {
                return ResponseEntity.ok("BookingMapping deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete BookingMapping");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    // @GetMapping("/checkIn/{bookingMappingid}")
    // public String getMethodName(@PathVariable("bookingMappingid") int bookingMappingid) {
    //     Optional<BookingMapping> bookingMapping_raw = bookingMappingRepository.findById(bookingMappingid);
    //     BookingMapping bookingMapping = bookingMapping_raw.get();
    //     Booking booking = bookingRepository.findByBookingID(bookingMapping.getBookingID().getBookingID());

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //     LocalDate checkIn = LocalDate.parse(dateFormat.format(bookingMapping.getCheckInDate()), formatter);
    //     LocalDate checkOut = LocalDate.parse(dateFormat.format(bookingMapping.getCheckOutDate()), formatter);
    //     int totalBookedDays = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

    //     int totalPriceRoom = bookingMapping.getBookingTotalAmount() * totalBookedDays;

    //     Invoice newInvoice = new Invoice();
    //     newInvoice.setBooking(booking);
    //     newInvoice.setCustomerName(booking.getCustomerID().getCustomerName());
    //     newInvoice.setTotalAmount(totalPriceRoom);
    //     newInvoice.setInvoiceDate(bookingMapping.getCheckInDate());
    //     invoiceRepository.save(newInvoice);

    //     bookingMapping.setBookingMappingActive(2);
    //     bookingMappingRepository.save(bookingMapping);
    //     return "redirect:/receptionist/bookingdetail?id=" + booking.getBookingID();
    // }

    @GetMapping("/checkIn/{bookingMappingid}")
    public ResponseEntity<String> getMethodName(@PathVariable("bookingMappingid") int bookingMappingid) {

        try {
            Optional<BookingMapping> bookingMapping_raw = bookingMappingRepository.findById(bookingMappingid);
            BookingMapping bookingMapping = bookingMapping_raw.get();
            Booking booking = bookingRepository.findByBookingID(bookingMapping.getBookingID().getBookingID());
    
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            LocalDate checkIn = LocalDate.parse(dateFormat.format(bookingMapping.getCheckInDate()), formatter);
            LocalDate checkOut = LocalDate.parse(dateFormat.format(bookingMapping.getCheckOutDate()), formatter);
            int totalBookedDays = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    
            int totalPriceRoom = bookingMapping.getBookingTotalAmount() * totalBookedDays;
    
            Invoice newInvoice = new Invoice();
            newInvoice.setBooking(booking);
            newInvoice.setCustomerName(booking.getCustomerID().getCustomerName());
            newInvoice.setTotalAmount(totalPriceRoom);
            newInvoice.setInvoiceDate(bookingMapping.getCheckInDate());
            newInvoice.setBookingMapping(bookingMapping);
            invoiceRepository.save(newInvoice);
    
            bookingMapping.setBookingMappingActive(2);
            bookingMappingRepository.save(bookingMapping);
            return ResponseEntity.ok("Check-In successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to inactive room");
        }
       
    }

    // @GetMapping("/inactiveBookingMapping/{id}")
    // public ResponseEntity<String> inactiveRoom(@PathVariable("id") int id) {
    //     try {
            
    //         return ResponseEntity.ok("Room inactive successfully");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to inactive room");
    //     }
    // }

}