package com.isp.project.controller;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isp.project.dto.RoomDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Employee;
import com.isp.project.model.Room;
import com.isp.project.repositories.BookingMappingRepository;
import com.isp.project.repositories.RoomRepository;
import com.isp.project.service.BookingMappingService;
import com.isp.project.service.EmployeeService;
import com.isp.project.service.RoomService;
import com.isp.project.service.RoomServiceImpl;
import com.isp.project.service.RoomTypeServiceImpl;
import com.isp.project.service.SeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private EmployeeService userService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingMappingRepository bookingMappingRepository;

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            Employee user = userService.findByEmail(email);
            m.addAttribute("user", user);
        }

    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) {
        Employee authenticatedUser = userService.authenticateUser(username, password);
        if (authenticatedUser != null) {
            // Authentication successful, store the user in the session
            session.setAttribute("loggedInUser", authenticatedUser);
            return "home";
        } else {
            // Authentication failed, add an error message to the redirect attributes
            model.addAttribute("loginError", "Invalid username or password");
            model.addAttribute("activeTab", "login");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @Autowired
    private RoomTypeServiceImpl roomTypeServiceImpl;

    @Autowired
    private RoomService roomService;

    @Autowired
    private SeService seService;

    @Autowired
    private BookingMappingService bookingMappingService;

    // @GetMapping("/receptionist/room")
    // public String listRoom(Model model, @RequestParam(name = "pageNo",
    // defaultValue = "1") Integer pageNo) {
    // Page<RoomDetailDTO> list = this.roomService.getAll(pageNo);

    // model.addAttribute("totalPage", list.getTotalPages());
    // model.addAttribute("currentPage", pageNo);

    // model.addAttribute("roomTypes",
    // roomTypeServiceImpl.getAllRoomTypesWithDetails());
    // model.addAttribute("rooms", list);
    // model.addAttribute("services", seService.getAllServiceDetail());
    // //model.addAttribute("services",
    // seService.findAllServiceDetailByServiceTypeId(1));
    // model.addAttribute("serviceTypes", seService.getAllServiceTypes());
    // return "room";
    // }
    @GetMapping("/receptionist/room")
    public String listRoom(Model model) {
        // Page<RoomDetailDTO> list = this.roomService.getAll(pageNo);

        // model.addAttribute("totalPage", list.getTotalPages());
        // model.addAttribute("currentPage", pageNo);
        List<BookingMapping> list_All = roomService.getAllRoom();

        List<BookingMapping> list = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();

        for (BookingMapping bookingMapping : list_All) {
            if (bookingMapping.getBookingMappingActive() == 1 ||
                    bookingMapping.getBookingMappingActive() == 2) {
                list.add(bookingMapping);
            }
        }

        model.addAttribute("roomTypes",
                roomTypeServiceImpl.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", list);
        model.addAttribute("services", seService.getAllServiceDetail());
        // //model.addAttribute("services",
        // seService.findAllServiceDetailByServiceTypeId(1);
        model.addAttribute("serviceTypes", seService.getAllServiceTypes());
        return "room";
    }

    // @GetMapping("/receptionist/room")
    // public String listRoom(Model model,
    // @RequestParam("checkinDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date
    // checkinDate) {
    // // Page<RoomDetailDTO> list = this.roomService.getAll(pageNo);

    // // model.addAttribute("totalPage", list.getTotalPages());
    // // model.addAttribute("currentPage", pageNo);
    // List<BookingMapping> list_All = new ArrayList<>();
    // if (checkinDate == null) {
    // list_All = roomService.getAllRoom();
    // } else {
    // list_All = bookingMappingService.findAvailableRooms(checkinDate);
    // }
    // List<BookingMapping> list = new ArrayList<>();

    // for (BookingMapping bookingMapping : list_All) {
    // if (bookingMapping.getBookingMappingActive() == 1 ||
    // bookingMapping.getBookingMappingActive() == 2) {
    // list.add(bookingMapping);
    // }
    // }

    // model.addAttribute("roomTypes",
    // roomTypeServiceImpl.getAllRoomTypesWithDetails());
    // model.addAttribute("rooms", list);
    // model.addAttribute("services", seService.getAllServiceDetail());
    // // model.addAttribute("services",
    // // seService.findAllServiceDetailByServiceTypeId(1));
    // model.addAttribute("serviceTypes", seService.getAllServiceTypes());
    // return "room";
    // }

    // @GetMapping("/receptionist/roomtest")
    // public List<BookingMapping> listRoom1(
    // @RequestParam("checkinDate") Date checkinDate) {

    // List<BookingMapping> list_All = new ArrayList<>();
    // if (checkinDate == null) {
    // list_All = roomService.getAllRoom();
    // } else {
    // list_All = bookingMappingService.findAvailableRooms(checkinDate);
    // }
    // List<BookingMapping> list = new ArrayList<>();

    // for (BookingMapping bookingMapping : list_All) {
    // if (bookingMapping.getBookingMappingActive() == 1 ||
    // bookingMapping.getBookingMappingActive() == 2) {
    // list.add(bookingMapping);
    // }
    // }

    // return list;

    // }

    // ========================
    // @GetMapping("/receptionist/roomtest")
    // @GetMapping("/receptionist/roomtest")
    // @ResponseBody
    // public List<BookingMapping> getAvailableRooms(@RequestParam(required = false)
    // String checkinDate) {
    // if (checkinDate != null) {
    // Date date = Date.valueOf(checkinDate);
    // return bookingMappingService.getAvailableRooms(date);
    // } else {
    // return bookingMappingService.getAvailableRooms(null); // Fetch all rooms if
    // no date is provided
    // }
    // }
    // @GetMapping("/receptionist/roomtest")
    // @ResponseBody
    // public List<BookingMapping> getAvailableRooms(@RequestParam(required = false)
    // String checkinDate) {
    // if (checkinDate == null || checkinDate.isEmpty()) {
    // // Return all BookingMapping records if checkinDate is not set
    // return bookingMappingRepository.findAll();
    // } else {
    // // Return BookingMapping records filtered by checkinDate
    // Date date = Date.valueOf(checkinDate);
    // return bookingMappingRepository.findByCheckInDateLessThanEqual(date);
    // }
    // }
    // ===================đã lấy được
    // ========================================================================
    // @GetMapping("/receptionist/roomtest")
    // @ResponseBody
    // public ResponseEntity<List<BookingMapping>>
    // getAvailableRooms(@RequestParam(required = false) String checkinDate) {
    // List<BookingMapping> bookingMappings;
    // if (checkinDate == null || checkinDate.isEmpty()) {
    // bookingMappings = bookingMappingRepository.findAll();
    // } else {
    // Date date = Date.valueOf(checkinDate);
    // bookingMappings =
    // bookingMappingRepository.findByCheckInDateLessThanEqual(date);
    // }

    // return ResponseEntity.ok(bookingMappings);
    // }
    // ===================đã lấy được
    // ========================================================================

    @GetMapping("/receptionist/roomtest")
    @ResponseBody
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(@RequestParam(required = false) String checkinDate) {
        List<RoomDTO> bookingMappings;
        List<RoomDTO> list = new ArrayList<>();

        if (checkinDate == null || checkinDate.isEmpty()) {
            bookingMappings = roomRepository.findAllRooms();
        } else {
            Date date = Date.valueOf(checkinDate);
            bookingMappings = roomRepository.findAllRoomsWithCheckInDate(date);
        }
        for (RoomDTO bookingMapping : bookingMappings) {
            if (bookingMapping.getBookingMappingActive() == 1 ||
                    bookingMapping.getBookingMappingActive() == 2) {
                list.add(bookingMapping);
            }
        }

        return ResponseEntity.ok(list);
    }

    @GetMapping("/detail")
    public String detailR(@RequestParam("roomTypeId") Integer roomTypeId, Model model) {
        model.addAttribute("roomType", roomTypeServiceImpl.getRoomTypeDetailById(roomTypeId));
        return "detail";
    }

    @GetMapping("/home")
    public String room(Model model) {
        model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
        return "home";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

}