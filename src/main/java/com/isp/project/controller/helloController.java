package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


 @Controller
 @RequestMapping("/home")
public class helloController {
    
    
    @GetMapping("/table")
    public String table(){      
        return "table.html"; 
    }  
    
    @GetMapping("/invoid")
    public String invoice(){      
        return "invoice.html"; 
    }  
}
