package com.isp.project.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isp.project.dto.InvoiceLineDTO;
import com.isp.project.dto.RoomCustomerDTO;
import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.dto.ServiceDetailDTO;
import com.isp.project.model.BookingMapping;
import com.isp.project.model.Customer;
import com.isp.project.model.Invoice;
import com.isp.project.model.InvoiceLine;
import com.isp.project.model.Room;
import com.isp.project.model.RoomType;
import com.isp.project.model.Service;
import com.isp.project.repositories.InvoiceLineRepository;
import com.isp.project.repositories.InvoiceRepository;
import com.isp.project.repositories.RoomRepository;
import com.isp.project.repositories.ServiceRepository;
import com.isp.project.service.InvoiceLineService;
import com.isp.project.service.RoomService;
import com.isp.project.service.RoomServiceImpl;
import com.isp.project.service.RoomTypeService;
import com.isp.project.service.SeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
// @RequestMapping("api/room")

public class RoomController {
    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private InvoiceLineRepository invoiceLineRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private SeService seService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private InvoiceLineService invoiceLineService;

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
    public String getRoomFromFilter(@RequestParam("statusFilter") String statusFilter,
            @RequestParam(required = false) Integer selectedRoomTypeId, Model model) {

        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomService.getAllRoomsByStatus(statusFilter));
        model.addAttribute("services", seService.getAllServiceDetail());
        // model.addAttribute("services",
        // seService.findAllServiceDetailByServiceTypeId(1));
        model.addAttribute("serviceTypes", seService.getAllServiceTypes());
        return "room";
    }

    @GetMapping("/filterRoomType")
    public String filter(@RequestParam("selectedRoomTypeId") Integer id, Model model,
            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

        Page<RoomDetailDTO> list = this.roomService.getAllRoomsWithDetailsByRoomTypeId(id, pageNo);

        model.addAttribute("selectedRoomTypeId", id);

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);

        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", list);
        return "filterRoomType";
    }

    @PostMapping("/filter-status")
    public String filterStatus(@RequestParam("statusFilter") String status, Model model) {
        model.addAttribute("roomTypes", roomTypeService.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", roomServiceImpl.getAllRoomsByStatus(status));
        return "filterRoomType";
    }

    // @GetMapping("/editRoom")
    // public ResponseEntity<Map<Object, Object>> getRoom(@RequestParam("roomId")
    // Integer roomId) {
    // Customer customer = roomServiceImpl.test(roomId);
    // Room room = roomServiceImpl.testR(roomId);

    // Map<Object, Object> response = new HashMap<>();
    // response.put("customer", customer);
    // response.put("room", room);

    // return ResponseEntity.ok(response);
    // }

    // @GetMapping("/editRoom")
    // public ResponseEntity<RoomCustomerDTO> getRoom(@RequestParam("roomId")
    // Integer roomId) {
    // RoomCustomerDTO roomCustomerDTO =
    // roomServiceImpl.getAllRoomCusWithDetailsByRoomId(roomId);
    // return ResponseEntity.ok(roomCustomerDTO);
    // }

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
    public ResponseEntity<List<ServiceDetailDTO>> getService2(@RequestParam("seName") String seName) {
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

    

    // @PostMapping("/addInvoiceLine")
    // @ResponseBody
    // public ResponseEntity<String> addInvoiceLines(@RequestBody String selectedInvoiceLine1JSON) {
    //     try {
    //         List<InvoiceLineDTO> selectedInvoiceLines = convertJsonToInvoiceLineList(selectedInvoiceLine1JSON);
    //         for (InvoiceLineDTO invoiceLine : selectedInvoiceLines) {
    //             Service service = serviceRepository.findById(invoiceLine.getSeID()).orElse(null);
    //             Invoice invoice = invoiceRepository.findById(invoiceLine.getInvoiceID()).orElse(null);
    //             if (service != null && invoice != null) {
    //                 InvoiceLine invoiceLine2 = new InvoiceLine();
    //                 invoiceLine2.setInvoiceTotalAmount(invoiceLine.getInvoiceTotalAmount());
    //                 invoiceLine2.setService(service);
    //                 invoiceLine2.setQuantity(invoiceLine.getQuantity());
    //                 invoiceLine2.setInvoice(invoice);

    //                 invoiceLineRepository.save(invoiceLine2);
    //             }
    //         }
    //         return ResponseEntity.ok("Saved successfully");
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body("Error saving invoice lines: " + e.getMessage());
    //     }
    // }



    // private List<InvoiceLineDTO> convertJsonToInvoiceLineList(String json) {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     List<InvoiceLineDTO> invoiceLines = new ArrayList<>();
    //     try {
    //         InvoiceLineDTO[] invoiceLineArray = objectMapper.readValue(json, InvoiceLineDTO[].class);
    //         invoiceLines = Arrays.asList(invoiceLineArray);
    //     } catch (JsonProcessingException e) {
    //         e.printStackTrace();
    //     }
    //     return invoiceLines;
    // }

    @PostMapping("/addInvoiceLine")
    @ResponseBody
    public ResponseEntity<String> addInvoiceLines(@RequestBody String invoiceLineData) {
        System.out.println("Received POST request to /addInvoiceLine");

        List<Map<String, Object>> selectedInvoiceLines = convertJsonToList(invoiceLineData);

        for (Map<String, Object> lineData : selectedInvoiceLines) {
            try {
                Double invoiceTotalAmount = Double.valueOf(lineData.get("InvoiceTotalAmount").toString());
                Integer seId = Integer.valueOf(lineData.get("SeID").toString());
                Integer quantity = Integer.valueOf(lineData.get("Quantity").toString());
                Integer invoiceId = Integer.valueOf(lineData.get("InvoiceID").toString());

                Optional<Service> newServiceOptional = serviceRepository.findById(seId);
                Optional<Invoice> newInvoiceOptional = invoiceRepository.findById(invoiceId);

                if (newServiceOptional.isPresent() && newInvoiceOptional.isPresent()) {
                    Service newService = newServiceOptional.get();
                    Invoice newInvoice = newInvoiceOptional.get();

                    // Create and save the InvoiceLine
                    InvoiceLine invoiceLine = new InvoiceLine();
                    invoiceLine.setInvoiceTotalAmount(invoiceTotalAmount);
                    invoiceLine.setService(newService);
                    invoiceLine.setQuantity(quantity);
                    invoiceLine.setInvoice(newInvoice);

                    invoiceLineRepository.save(invoiceLine);
                } else {
                    // Handle case where service or invoice is not found
                    System.out.println("Service or Invoice not found for IDs: " + seId + ", " + invoiceId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Service or Invoice not found for IDs: " + seId + ", " + invoiceId);
                }
            } catch (NumberFormatException e) {
                // Handle parsing errors if necessary
                System.out.println("Error parsing numbers from JSON data: " + e.getMessage());
                return ResponseEntity.badRequest().body("Error parsing numbers from JSON data: " + e.getMessage());
            }
        }

        return ResponseEntity.ok("Saved successfully");
    }

    private List<Map<String, Object>> convertJsonToList(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> list = null;
        try {
            list = objectMapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
