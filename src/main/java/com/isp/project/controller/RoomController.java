package com.isp.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.isp.project.model.RoomType;
import com.isp.project.service.RoomTypeServiceImpl;

import jakarta.validation.Valid;

@Controller
public class RoomController {
    @Autowired
    private RoomTypeServiceImpl roomTypeServiceImpl;
    @GetMapping("/managerbooking")
    public String ManagerBooking() {
        return "ManagerBooking";
    }

    @GetMapping("/roomcategory")
    public String RoomCategory(Model model) {
        model.addAttribute("listRoomType", roomTypeServiceImpl.getAll());
        return "RoomCategory";
    }

    @GetMapping("/add-cate")
    public String add1(Model model) {
        RoomType roomType = new RoomType();
        model.addAttribute("roomType", roomType);
        return "addRoomType";
    }

    public RoomController(RoomTypeServiceImpl roomTypeServiceImpl) {
        this.roomTypeServiceImpl = roomTypeServiceImpl;
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
        if (this.roomTypeServiceImpl.create(roomType)) {
            return "redirect:/roomcategory";
        } else {
            return "redirect:/add-cate";
        }
    }

    @GetMapping("/list/{id}/edit")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("roomType", roomTypeServiceImpl.findByID(id));
        return "updateRoomType";
    }

    @PostMapping("/saveRoomType")
    public String updated(@Valid @ModelAttribute("roomType") RoomType roomType, BindingResult bindingResult, Model model) {
        // if(result.hasErrors()){
        //     return "updateRoomType";
        // }
        
        //roomTypeServiceImpl.update(roomType);
        // model.addAttribute("listRoomType", roomTypeService.getAllRoomType());
        
        //return "redirect:/roomcategory";
        if (bindingResult.hasErrors()) {
            return "updateRoomType"; // Trả về lại trang hiện tại nếu có lỗi
        }
        if (roomType.getPriceHour() <= 0 || roomType.getPriceDay() <= 0) {
            bindingResult.rejectValue("priceHour", "PositiveValue");
            bindingResult.rejectValue("priceDay", "PositiveValue");
            return "updateRoomType";
        }
        if (this.roomTypeServiceImpl.create(roomType)) {
            return "redirect:/roomcategory";
        } else {
            return "redirect:/add-cate";
        }
    }

    // @GetMapping("/list/{RoomTypeID}/delete")
    // public String delete(@PathVariable("RoomTypeID") Integer id, Model model) {
    //     roomTypeService.delete(id);
    //     return "redirect:/roomcategory";
    // }

    @GetMapping("/deleteRo/{RoomTypeID}")
    public ResponseEntity<String> deleteBooking(@PathVariable("RoomTypeID") Integer id) {
        try {
            boolean deleted = roomTypeServiceImpl.delete(id);;
            if (deleted) {
                return ResponseEntity.ok("Room category deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete room category");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

}
