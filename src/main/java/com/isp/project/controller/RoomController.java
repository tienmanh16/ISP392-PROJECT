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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isp.project.dto.RoomCustomerDTO;
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

@Controller
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

    @GetMapping("/managerbooking")
    public String ManagerBooking() {
        return "ManagerBooking";
    }

    @GetMapping("/roomcategory")
    public String RoomCategory() {
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

     
}
