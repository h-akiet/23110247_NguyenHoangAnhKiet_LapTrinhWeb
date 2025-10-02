package com.example.demo.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        String currentRole = (String) session.getAttribute("userRole"); 
        String requestURI = request.getRequestURI();

        // 1. KIỂM TRA ĐĂNG NHẬP (Unauthenticated Check)
        if (currentRole == null) {
            // Cho phép truy cập /login, /register, /logout và static files
            if (requestURI.contains("/login") || requestURI.contains("/register") || 
                requestURI.contains("/static") || requestURI.contains("/logout")) {
                return true; 
            }
            
            // Nếu cố gắng truy cập trang bảo vệ -> Chuyển hướng về login
            response.sendRedirect(request.getContextPath() + "/login");
            return false; // Chặn request
        }

        // 2. LOGIC PHÂN QUYỀN (Authorization Check)

        // Yêu cầu truy cập khu vực Admin (URL bắt đầu bằng /admin)
        if (requestURI.startsWith("/admin")) { 
            if ("ADMIN".equals(currentRole)) {
                return true; // ADMIN được phép
            } else {
                // USER cố gắng truy cập Admin -> Chuyển về login lại
                response.sendRedirect(request.getContextPath() + "/login"); 
                return false; // Chặn request
            }
        } 
        
        // Yêu cầu truy cập khu vực User (URL bắt đầu bằng /user)
        if (requestURI.startsWith("/user")) {
            // Giả định cả USER và ADMIN đều có thể vào trang user/home
            return true; 
        }
        
        // Cho phép các request đã đăng nhập đi tiếp nếu không thuộc /admin hay /user
        return true; 
    }
}