package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomTypeRepository;
import com.isp.project.service.RoomTypeService;



@Controller
public class RoomController {

@Autowired
private RoomTypeService roomTypeService;

    @GetMapping("managerbooking")
    public String ManagerBooking() {
        return "ManagerBooking";
    }

    @GetMapping("roomcategory")
    public String RoomCategory(Model model) {
        List<RoomType> roomType = this.roomTypeService.getAllRoomType();
        model.addAttribute("listRoomType", roomType);
        if(roomType==null){
            return "Invoice";
        }
        return "RoomCategory";
    }

    @GetMapping("/add-cate")
    public String add1(Model model){
        RoomType roomType = new RoomType();
        model.addAttribute("roomType", roomType);
        return "addRoomType";
    }
    
    @PostMapping("/addRoomType")
    public String save(@ModelAttribute("roomType") RoomType roomType){
        if (this.roomTypeService.create(roomType)) {
            return "redirect:/roomcategory";
        } else {
            return "redirect:/addRoomType";
        }
    }

    

    
     
}
