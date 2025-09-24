package vn.iotstar.Controller.api;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.iotstar.Entity.Category;
import vn.iotstar.Model.Response;
import vn.iotstar.Service.CategoryService;
import vn.iotstar.Service.IStorageService;

@RestController
@RequestMapping(path = "/api/category")
@CrossOrigin(origins = "http://localhost:8088") // Thêm dòng này để tránh lỗi CORS
public class CategoryAPIController {
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private IStorageService storageService;
    
    // Phương thức đã sửa lỗi constructor
    @GetMapping
    public ResponseEntity<Response<Page<Category>>> getAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryService.findAll(pageable);
        return new ResponseEntity<>(
                new Response<>(true, "Thành công", categoryPage), 
                HttpStatus.OK
        );
    }
    
    // Phương thức đã sửa lỗi constructor
    @PostMapping(path = "/getCategory")
    public ResponseEntity<Response<Category>> getCategory(@Validated @RequestParam("id") int id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(
                    new Response<>(true, "Thành công", category.get()), 
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new Response<>(false, "Thất bại", null), 
                    HttpStatus.NOT_FOUND
            );
        }
    }
    
    // Phương thức đã sửa lỗi constructor và tên tham số
    @PostMapping(path = "/addCategory")
    public ResponseEntity<Response<Category>> addCategory(
            @Validated @RequestParam("categoryName") String categoryName,
            @Validated @RequestParam("images") MultipartFile images) {
        // ... (rest of your code)

        Category category = new Category();
        if (!images.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();

            // Lấy filename từ service
            String filename = storageService.getStorageFilename(images, uuString); 

            // Lưu file với filename hợp lệ
            storageService.store(images, filename); 

            // Cập nhật trường images của category
            category.setImages(filename);
        }

        category.setCategoryName(categoryName);
        categoryService.save(category);
        return new ResponseEntity<>(
                new Response<>(true, "Thêm Thành công", category), 
                HttpStatus.OK
        );
    }
    
    @PutMapping(path = "/updateCategory")
    public ResponseEntity<Response<Category>> updateCategory(
            @Validated @RequestParam("categoryId") int categoryId,
            @Validated @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "images", required = false) MultipartFile images) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(
                    new Response<>(false, "Không tìm thấy Category", null), 
                    HttpStatus.BAD_REQUEST
            );
        }

        Category category = optCategory.get();
        if (images != null && !images.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuString = uuid.toString();

            // Lấy filename từ service
            String filename = storageService.getStorageFilename(images, uuString);

            // Lưu file
            storageService.store(images, filename);

            // Cập nhật trường images
            category.setImages(filename);
        }
        category.setCategoryName(categoryName);
        categoryService.save(category);
        return new ResponseEntity<>(
                new Response<>(true, "Cập nhật Thành công", category), 
                HttpStatus.OK
        );
    }
    // Phương thức đã sửa lỗi constructor
    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<Response<Category>> deleteCategory(
            @Validated @RequestParam("categoryID") int categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(
                    new Response<>(false, "Không tìm thấy Category", null), 
                    HttpStatus.BAD_REQUEST
            );
        }
        
        categoryService.delete(optCategory.get());
        return new ResponseEntity<>(
                new Response<>(true, "Xóa Thành công", optCategory.get()), 
                HttpStatus.OK
        );
    }
}