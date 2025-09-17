package com.example.demo.Controller;



import com.example.demo.Entity.Category;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.util.StringUtils;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("message", "Danh sách categories đã được tải sau khi đăng nhập!");
        return "admin/categories/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("category", new Category()); // Truyền một đối tượng Category rỗng để form add
        return "admin/categories/add";
    }

//    @PostMapping("saveOrUpdate") // Xử lý cả thêm mới và cập nhật
//    public ModelAndView saveOrUpdate(@ModelAttribute("category") Category category, Model model) {
//        categoryService.save(category);
//        model.addAttribute("message", "Category saved successfully!");
//        return new ModelAndView("redirect:/admin/categories", model);
//    }

    @GetMapping("edit/{categoryId}")
    public String edit(@PathVariable("categoryId") int categoryId, Model model) {
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/categories/edit"; // Sử dụng lại form add/edit
        }
        model.addAttribute("message", "Category not found!");
        return "redirect:/admin/categories";
    }

//    @GetMapping("delete/{categoryId}")
//    public ModelAndView delete(@PathVariable("categoryId") int categoryId, Model model) {
//        categoryService.deleteById(categoryId);
//        model.addAttribute("message", "Category is deleted!");
//        return new ModelAndView("redirect:/admin/categories", model);
//    }

    @GetMapping("search")
    public String search(Model model, @RequestParam(name = "name", required = false) String name) {
        List<Category> list = null;
        if (StringUtils.hasText(name)) { // Kiểm tra xem tham số name có nội dung không
            list = categoryService.findByCategoryNameContaining(name);
        } else {
            list = categoryService.findAll(); // Nếu không có tên tìm kiếm, hiển thị tất cả
        }
        model.addAttribute("categories", list);
        model.addAttribute("searchName", name); // Truyền lại tên tìm kiếm để giữ trên form
        return "admin/categories/list"; // Sử dụng lại list.jsp cho kết quả tìm kiếm
    }

    @GetMapping("view/{categoryId}")
    public String view(@PathVariable("categoryId") int categoryId, Model model) {
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/categories/view"; // Cần tạo file view này
        }
        model.addAttribute("message", "Category not found!");
        return "redirect:/admin/categories";
    }
}