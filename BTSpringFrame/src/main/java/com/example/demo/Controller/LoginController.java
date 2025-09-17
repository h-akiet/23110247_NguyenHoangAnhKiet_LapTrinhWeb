package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;

import java.util.Optional; // Import Optional

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Tên file JSP cho trang login
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              ModelMap model) {
        
        Optional<User> userOptional = userService.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) { // Kiểm tra xem Optional có chứa giá trị User không
            User user = userOptional.get(); // Lấy đối tượng User từ Optional
            
            // Chuyển hướng dựa trên roleid
            if (user.getRoleid() == 1) { // Giả sử roleid = 1 là Admin
            	model.addAttribute("error", "Đăng nhập thành công!");
            	return "redirect:/admin/categories";
            } else {
                return "redirect:/user"; // Chuyển đến trang người dùng thông thường
            }
        } else {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            return "login"; // Quay lại trang login với thông báo lỗi
        }
    }
}