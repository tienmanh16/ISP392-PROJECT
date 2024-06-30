package com.isp.project.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isp.project.dto.RoomDetailDTO;
import com.isp.project.model.Employee;
import com.isp.project.model.Room;
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

    @GetMapping("/receptionist/room")
    public String listRoom(Model model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
        Page<RoomDetailDTO> list = this.roomService.getAll(pageNo);

        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);

        model.addAttribute("roomTypes", roomTypeServiceImpl.getAllRoomTypesWithDetails());
        model.addAttribute("rooms", list);
        model.addAttribute("services", seService.getAllServiceDetail());
        //model.addAttribute("services", seService.findAllServiceDetailByServiceTypeId(1));
        model.addAttribute("serviceTypes", seService.getAllServiceTypes());
        return "room"; 
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