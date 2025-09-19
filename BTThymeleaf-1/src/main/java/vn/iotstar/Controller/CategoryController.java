package vn.iotstar.Controller;

import vn.iotstar.Entity.Category;
import vn.iotstar.Entity.Video;
import vn.iotstar.Service.CategoryService;
import vn.iotstar.Service.VideoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private VideoService videoService;

    @GetMapping
    public String listCategories(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "name", required = false) String name,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage;

        if (StringUtils.hasText(name)) {
            categoryPage = categoryService.findByCategoryNameContaining(name, pageable);
            model.addAttribute("searchName", name);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", categoryPage.getNumber());
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("totalItems", categoryPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("message", "Danh sách danh mục đã được tải!");

        return "admin/categories/list";
    }

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("category", new Category());
        return "admin/categories/add";
    }

    @PostMapping("saveOrUpdate")
    public ModelAndView saveOrUpdate(
            @ModelAttribute("category") @Validated Category category,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin/categories/edit");
        }
        try {
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("message", "Danh mục đã được lưu thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi lưu danh mục: " + e.getMessage());
            return new ModelAndView("admin/categories/edit");
        }
        return new ModelAndView("redirect:/admin/categories");
    }

    @GetMapping("edit/{categoryId}")
    public String edit(@PathVariable("categoryId") int categoryId, Model model) {
        Category category = categoryService.findById(categoryId).orElse(null);
        if (category != null) {
            model.addAttribute("category", category);
            return "admin/categories/edit";
        }
        model.addAttribute("error", "Không tìm thấy danh mục!");
        return "redirect:/admin/categories";
    }

    @GetMapping("delete/{categoryId}")
    public ModelAndView delete(@PathVariable("categoryId") int categoryId, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteById(categoryId);
            redirectAttributes.addFlashAttribute("message", "Danh mục đã được xóa thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi xóa danh mục: " + e.getMessage());
        }
        return new ModelAndView("redirect:/admin/categories");
    }

    @GetMapping("search")
    public String search(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage;

        if (StringUtils.hasText(name)) {
            categoryPage = categoryService.findByCategoryNameContaining(name, pageable);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }

        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", categoryPage.getNumber());
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("totalItems", categoryPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("searchName", name);

        return "admin/categories/list";
    }

    @GetMapping("view/{categoryId}")
    public String view(@PathVariable("categoryId") int categoryId, Model model, RedirectAttributes redirectAttributes) {
        List<Video> videos = videoService.findByCategoryId(categoryId);
        model.addAttribute("videos", videos);
        return "admin/categories/view";
    }
}