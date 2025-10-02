package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import jakarta.servlet.http.HttpSession; // 👈 Import cần thiết cho Session

import java.util.Optional; 

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Trả về file login.html
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              ModelMap model,
                              HttpSession session) { // 👈 Thêm tham số HttpSession
        
        // Giả định: userService.findByUsernameAndPassword(username, password) trả về Optional<User>
        Optional<User> userOptional = userService.findByUsernameAndPassword(username, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get(); 
            String role = null; 
            // Xác định Role (Giả sử roleid 1 là ADMIN, còn lại là USER)
            if (user.getRoleid() == 1) {
                role = "ADMIN";
            } else if (user.getRoleid() == 2) {
                role = "USER";
            } else {
                // Trường hợp roleid không hợp lệ (ví dụ: 0, 3,...)
                model.addAttribute("error", "Tài khoản của bạn có vai trò không hợp lệ. Vui lòng liên hệ quản trị viên.");
                return "login"; 
            }

            // 1. LƯU THÔNG TIN CẦN THIẾT VÀO SESSION (QUAN TRỌNG CHO INTERCEPTOR)
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("user", user); // Lưu đối tượng User (tùy chọn)
            session.setAttribute("userRole", role); // 👈 Interceptor sẽ đọc giá trị này

            // 2. Chuyển hướng theo Role (Sử dụng đường dẫn đã cấu hình trong Interceptor)
            if ("ADMIN".equals(role)) {
                // Chuyển hướng về trang chủ Admin
                return "redirect:/admin/categories"; 
            } else {
                // Chuyển hướng về trang chủ User
                return "redirect:/users/home"; 
            }
        } else {
            // Đăng nhập thất bại
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
            return "login"; 
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa toàn bộ Session
        return "redirect:/login"; // Chuyển về trang đăng nhập
    }
    @GetMapping("/users/home")
    public String userHome() {
        // Trả về file JSP: /WEB-INF/views/user/home.jsp
        return "users/userhome"; 
    }
}