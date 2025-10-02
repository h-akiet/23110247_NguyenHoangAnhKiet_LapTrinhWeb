package com.example.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                // Áp dụng Interceptor cho các URL bảo vệ (admin, user, logout)
                .addPathPatterns("/admin/**", "/user/**", "/logout")
                
                // Loại trừ các URL công khai để tránh lỗi vòng lặp chuyển hướng
                .excludePathPatterns("/login", "/register", "/index", "/")
                
                // Nếu bạn có thư mục static ở ngoài (src/main/resources/static),
                // bạn nên loại trừ nó để tải CSS/JS
                .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**");
    }
}