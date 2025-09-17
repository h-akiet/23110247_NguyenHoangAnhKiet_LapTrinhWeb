package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    
    // Trang chủ sẽ chuyển hướng đến trang đăng nhập
    @GetMapping("")
    public String home() {
        return "redirect:/login"; 
    }
}
