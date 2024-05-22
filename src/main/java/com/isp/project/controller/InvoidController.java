package com.isp.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


 @Controller

public class InvoidController {
    
    
    @GetMapping("/listinvoid")
    public String table(){      
        return "listInvoid.html"; 
    }  
    
    @GetMapping("/invoid")
    public String invoice(){      
        return "invoice.html"; 
    }  
}
