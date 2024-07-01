package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.isp.project.model.RoomItem;
import com.isp.project.repositories.RoomItemRepository;
import com.isp.project.service.RoomItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class RoomItemController {

    @Autowired
    RoomItemRepository roomItemRepository;

    @Autowired
    RoomItemService roomItemService;

    @GetMapping("/roomitems")
    public String listRoomItem(
            @RequestParam(value = "table_search_roomitems", required = false) String queryItemName, Model model) {
        List<RoomItem> listRoomItem;
        if (queryItemName != null && !queryItemName.isEmpty()) {
            listRoomItem = roomItemService.findRoomItemsByNameContaining(queryItemName.toLowerCase());

        } else {
            listRoomItem = roomItemService.getAllRoomItem();
        }
        model.addAttribute("roomItemList", listRoomItem);
        model.addAttribute("queryItemName", queryItemName);
        model.addAttribute("addRoomItem", new RoomItem());
        return "roomitems";
    }

    @GetMapping("/updateRoomitem")
    public String showUpdateRoomItem(@RequestParam("idRoomItem") int roomitemId, Model model) {
        RoomItem roomItem = roomItemRepository.findById(roomitemId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        model.addAttribute("roomitem", roomItem);
        return "updateRoomItem";
    }

    @PostMapping("/updateRoomitem")
    public String updateRoomItem(@ModelAttribute("roomitem") RoomItem roomitem) {
        roomItemRepository.save(roomitem);
        return "redirect:/admin/roomitems"; // Assuming customerList is a page showing list of customers
    }

    @PostMapping("/addroomitem")
    public String addRoomItem(@ModelAttribute("addRoomItem") RoomItem roomitem) {
        roomItemRepository.save(roomitem);
        return "redirect:/admin/roomitems";
    }

    @PostMapping("/deleteRoomItem/{roomItemId}")
    public ResponseEntity<String> deleteBooking(@PathVariable("roomItemId") Integer roomItemId) {
        try {
            boolean deleted = roomItemService.deleteRoomItem(roomItemId);
            if (deleted) {
                return ResponseEntity.ok("Booking deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete booking");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // @GetMapping("/roomitems_check-name")
    // public ResponseEntity<Boolean> checkEmailExists(@RequestParam String
    // ItemName) {
    // boolean exists = roomItemService.existsByItemName(ItemName);
    // return ResponseEntity.ok(exists);
    // }
}
