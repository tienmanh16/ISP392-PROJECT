package com.isp.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.isp.project.model.RoomType;
import com.isp.project.repositories.RoomTypeRepository;



@Controller
public class RoomController {
@Autowired
private RoomTypeRepository roomTypeRepository;
    @GetMapping("managerbooking")
    public String ManagerBooking() {
        return "ManagerBooking";
    }

    @GetMapping("roomcategory")
    public String RoomCategory(Model model) {
        List<RoomType> roomType = this.roomTypeRepository.findAll();
        model.addAttribute("listRoomType", roomType);
        if(roomType==null){
            return "Invoice";
        }
        return "RoomCategory";
    }


    

    
     
}
