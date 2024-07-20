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

    @Autowired
    private EmployeeService employeeService;

  
    @GetMapping("/receptionist/room")
    public String listRoom(Model model, Principal p) {
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

        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }
        return "room";
    }

   
    @GetMapping("/receptionist/roomtest")
    @ResponseBody
    public ResponseEntity<List<RoomDTO>> getAvailableRooms1(@RequestParam(required = false) String checkinDate) {
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

    @GetMapping("/admin/room")
    public String listRoom1(Model model, Principal p) {
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

        if (p != null) {
            String email = p.getName();
            Employee user = employeeService.findByEmail(email);
            if (user != null) {
                model.addAttribute("user1", user);
            } else {
            }
        }
        return "room_admin";
    }

   
    @GetMapping("/admin/roomtest")
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
    

}