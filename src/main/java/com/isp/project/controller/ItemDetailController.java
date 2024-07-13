package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.isp.project.model.Room;
import com.isp.project.model.RoomItem;
import com.isp.project.model.RoomItemMapping;
import com.isp.project.service.ItemDetailService;
import com.isp.project.service.RoomItemService;
import com.isp.project.service.RoomService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class ItemDetailController {
    
    @Autowired
    RoomService roomService;

    @Autowired
    RoomItemService roomItemService; 

    @Autowired
    ItemDetailService itemDetailService;

    @GetMapping("/itemdetail_list")
    public String listItemDetail(Model model) {
        model.addAttribute("itemDeList", itemDetailService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("items", roomItemService.findAll());
        return "viewItemdetail";
    }

    @GetMapping("/itemdetail_add")
    public String add(Model model) {
        RoomItemMapping roomItemMapping = new RoomItemMapping();
        model.addAttribute("itemdetail", roomItemMapping);
        List<Room> rooms = roomService.findAll();
        model.addAttribute("rooms", rooms);
        List<RoomItem> items = roomItemService.findAll();
        model.addAttribute("items", items);
        return "addItemdetail";
    }

    @PostMapping("/itemdetail_add")
    public String save(Model model, @Valid @ModelAttribute("itemdetail") RoomItemMapping roomItemMapping) {
       // Kiểm tra trùng lặp
       if (itemDetailService.isDuplicate(roomItemMapping)) {
         RoomItemMapping existingMapping = itemDetailService.findDuplicate(roomItemMapping);
        if (existingMapping != null) {
            existingMapping.setQuantity(existingMapping.getQuantity() + roomItemMapping.getQuantity());
            itemDetailService.save(existingMapping);
            model.addAttribute("duplicate", "Add successfully");
            model.addAttribute("itemdetail", roomItemMapping);
            model.addAttribute("rooms", roomService.findAll());
            model.addAttribute("items", roomItemService.findAll());
            return "addItemdetail"; 
        }
    }
       
        itemDetailService.save(roomItemMapping);
        return "redirect:/admin/itemdetail_list";
    }
    
    @GetMapping("/itemdetail_edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        RoomItemMapping roomItemMapping = itemDetailService.findById(id);
        if (roomItemMapping != null) {
            model.addAttribute("itemdetail", roomItemMapping);
            model.addAttribute("rooms", roomService.findAll());
            model.addAttribute("items", roomItemService.findAll());
            return "editItemdetail"; 
        } else {
            return "redirect:/admin/itemdetail_list"; 
        }
    }

    @PostMapping("/itemdetail_edit")
    public String update(Model model, @Valid @ModelAttribute("itemdetail") RoomItemMapping roomItemMapping) {
        RoomItemMapping existingItemdetail = itemDetailService.findById(roomItemMapping.getRoomItemID());
        if (existingItemdetail != null) {
            existingItemdetail.setRoom(roomItemMapping.getRoom());
            existingItemdetail.setRoomItem(roomItemMapping.getRoomItem());
            existingItemdetail.setQuantity(roomItemMapping.getQuantity());
            itemDetailService.save(existingItemdetail);
        }
        return "redirect:/admin/itemdetail_list";
    }

    @GetMapping("/itemdetail_delete/{id}")
    public String delete(@PathVariable("id") int id) {
        RoomItemMapping roomItemMapping = itemDetailService.findById(id);
        if (roomItemMapping != null) {
            itemDetailService.deleteById(id);
        }
        return "redirect:/admin/itemdetail_list";
    }
}
