package com.isp.project.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.dto.ServiceDetailDTO;
import com.isp.project.model.Customer;
import com.isp.project.model.Room;
import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomRepository;
import com.isp.project.service.RoomService;
import com.isp.project.service.RoomServiceImpl;
import com.isp.project.service.RoomTypeService;
import com.isp.project.service.SeService;


import jakarta.validation.Valid;

@Controller
// @RequestMapping("/admin")
public class RoomController {
    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private SeService seService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping("/listRooms")
    public String listRooms(Model model) {
        List<RoomDetailDTO> rooms = roomService.getAllRoomsWithDetails();
        model.addAttribute("rooms", rooms);
        return "RoomList";
    }

    @GetMapping("/add-room")
    public String addRoom(Model model) {
        Room room = new Room();
        List<RoomType> roomTypes = roomTypeService.getAll();
        model.addAttribute("room", room);
        model.addAttribute("roomTypes", roomTypes);
        return "addRoom";
    }

    @PostMapping("/addRoom")
    public String saveRoom(@Valid @ModelAttribute("room") Room room, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addRoom";
        }
        if (this.roomService.create(room)) {
            return "redirect:/listRooms";
        } else {
            return "redirect:/add-room";
        }
    }

    @GetMapping("/listRooms/{id}/update")
    public String editRoom(@PathVariable("id") int id, Model model) {
        Room room = roomService.findById(id);
        if (room == null) {
            return "redirect:/listRooms";
        }
        model.addAttribute("room", room);
        model.addAttribute("roomType", room.getRoomType());
        return "updateRoom";
    }

    @PostMapping("/saveRoom")
    public String updateRoom(@Valid @ModelAttribute("room") Room room, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "updateRoom"; // Trả về lại trang hiện tại nếu có lỗi
        }
        if (this.roomService.create(room)) {
            return "redirect:/listRooms";
        } else {
            return "redirect:/add-room";
        }
    }

    @GetMapping("/managerbooking")
    public String ManagerBooking() {
        return "ManagerBooking";
    }

    @GetMapping("/listRoomType")
    public String RoomCategory(Model model, @Param("name") String name) {
        List<RoomType> listRoomType;
        if (name != null) {
            listRoomType = this.roomTypeService.searchRoomType(name);
        } else {
            listRoomType = this.roomTypeService.getAll();
        }
        model.addAttribute("listRoomType", listRoomType);
        return "RoomCategory";
    }

    @GetMapping("/add-cate")
    public String add1(Model model) {
        RoomType roomType = new RoomType();
        model.addAttribute("roomType", roomType);
        return "addRoomType";
    }

    @PostMapping("/addRoomType")
    public String save(@Valid @ModelAttribute("roomType") RoomType roomType, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "addRoomType";
        }
        if (roomType.getPriceHour() <= 0 || roomType.getPriceDay() <= 0) {
            bindingResult.rejectValue("priceHour", "PositiveValue");
            bindingResult.rejectValue("priceDay", "PositiveValue");
            return "addRoomType";
        }
        if (this.roomTypeService.create(roomType)) {
            return "redirect:/listRoomType";
        } else {
            return "redirect:/add-cate";
        }
    }

    @GetMapping("/list/{id}/update")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("roomType", roomTypeService.findByID(id));
        return "updateRoomType";
    }

    @GetMapping("/listRoomTypeActive")
    public String listRoomTypeActive(Model model) {
        List<RoomType> roomType = roomTypeService.findAllActive();
        model.addAttribute("listRoomType", roomType);
        return "RoomCategory";
    }

    @GetMapping("/editRoom")
    public ResponseEntity<Customer> getRoom(@RequestParam("roomId") Integer roomId) {
        Customer customer = roomServiceImpl.test(roomId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/filterRoom")
    public String getRoomFromFilter(@RequestParam("statusFilter") String statusFilter, @RequestParam(required = false) Integer selectedRoomTypeId, Model model) {


        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomService.getAllRoomsByStatus(statusFilter));
        model.addAttribute("services", seService.getAllServiceDetail());
        //model.addAttribute("services", seService.findAllServiceDetailByServiceTypeId(1));
        model.addAttribute("serviceTypes", seService.getAllServiceTypes());
        return "room";
    }

    @GetMapping("/filterRoomType")
    public String filter(@RequestParam("selectedRoomTypeId") Integer id, Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){

        Page<RoomDetailDTO> list = this.roomService.getAllRoomsWithDetailsByRoomTypeId(id, pageNo);

        model.addAttribute("selectedRoomTypeId", id);

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);

        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", list);
        return "filterRoomType";
    }

    @PostMapping("/filter-status")
    public String filterStatus(@RequestParam("statusFilter") String status, Model model){
        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomServiceImpl.getAllRoomsByStatus(status));
        return "filterRoomType";
    }


    @GetMapping("/editRoom2")
    public ResponseEntity<Room> getRoom2(@RequestParam("roomId") Integer roomId) {
        Room room = roomServiceImpl.testR(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/updateStatusRoom")
    public ResponseEntity<Room> updateRoom(@RequestParam("roomId") Integer roomId) {
        roomService.updateRoomStatusByRoomId(roomId);
        Room room = roomServiceImpl.testR(roomId);
        return ResponseEntity.ok(room);
    }

    @GetMapping("/getServiceBySeTypeId")
    public ResponseEntity<List<ServiceDetailDTO>> getService1(@RequestParam("serviceTypeId") Integer serviceTypeId) {
        List<ServiceDetailDTO> services = null;
        services = seService.findAllServiceDetailByServiceTypeId(serviceTypeId);
        if (serviceTypeId == 0) {
            services = seService.getAllServiceDetail();
        }

        return ResponseEntity.ok(services);
    }

    @GetMapping("/getServiceBySeName")
    public ResponseEntity<List<ServiceDetailDTO>> getService2(@RequestParam("seName") String seName){
        List<ServiceDetailDTO> services = null;
        services = seService.findAllServiceDetailBySeName(seName);
        if (seName == null) {
            services = seService.getAllServiceDetail();
        }
        return ResponseEntity.ok(services);
    }

    @GetMapping("/getAllService")
    public ResponseEntity<List<ServiceDetailDTO>> getAllService() {
        List<ServiceDetailDTO> services = seService.getAllServiceDetail();
        return ResponseEntity.ok(services);
    }


    @PostMapping("/api/availableRooms")
    @ResponseBody
    public List<RoomDetailDTO> getAvailableRooms(@RequestBody Map<String, String> dates) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String checkinDateStr = dates.get("checkinDate");
        String checkoutDateStr = dates.get("checkoutDate");

        System.out.println("Check-in Date: " + checkinDateStr);
        System.out.println("Check-out Date: " + checkoutDateStr);

        try {
            // Parse the strings into java.util.Date
            java.util.Date checkinDateUtil = sdf.parse(checkinDateStr);
            java.util.Date checkoutDateUtil = sdf.parse(checkoutDateStr);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date checkinDate = new java.sql.Date(checkinDateUtil.getTime());
            java.sql.Date checkoutDate = new java.sql.Date(checkoutDateUtil.getTime());

            return roomService.getAvailableRooms(checkinDate, checkoutDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid date format. Please use yyyy-MM-dd");
        }
    }

     
    @GetMapping("/listRoomTypeInactive")
    public String listRoomTypeInactive(Model model) {
        List<RoomType> roomType = roomTypeService.findAllInactive();
        model.addAttribute("listRoomType", roomType);
        return "RoomCategory";
    }

    @GetMapping("/inactiveRoomType/{id}")
    public ResponseEntity<String> inactiveRoomType(@PathVariable("id") int id) {
        try {
            roomTypeService.updateRoomTypeActiveStatus(id, 0);
            return ResponseEntity.ok("Room category inactive successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to inactive room type");
        }
    }

    @GetMapping("/activeRoomType/{id}")
    public ResponseEntity<String> activeRoomType(@PathVariable("id") int id) {
        try {
            roomTypeService.updateRoomTypeActiveStatus(id, 1);
            return ResponseEntity.ok("Room category active successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to active room type");
        }
    }

    @PostMapping("/saveRoomType")
    public String updateRoomType(@Valid @ModelAttribute("roomType") RoomType roomType, BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "updateRoomType"; // Trả về lại trang hiện tại nếu có lỗi
        }
        if (roomType.getPriceHour() <= 0 || roomType.getPriceDay() <= 0) {
            bindingResult.rejectValue("priceHour", "PositiveValue");
            bindingResult.rejectValue("priceDay", "PositiveValue");
            return "updateRoomType";
        }
        if (this.roomTypeService.create(roomType)) {
            return "redirect:/listRoomType";
        } else {
            return "redirect:/add-cate";
        }
    }
}
