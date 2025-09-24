package vn.iotstar.Controller.api;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.Entity.Category;
import vn.iotstar.Entity.Product;
import vn.iotstar.Model.Response;
import vn.iotstar.Service.CategoryService;
import vn.iotstar.Service.ProductService;
import vn.iotstar.Service.IStorageService;

@RestController
@RequestMapping(path = "/api/product")
public class ProductApiController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    IStorageService storageService;

    @GetMapping
    public ResponseEntity<?> getAllProduct() {
        return new ResponseEntity<Response>(
                new Response(true, "Thành công", productService.findAll()),
                HttpStatus.OK);
    }

    @PostMapping(path = "/addProduct")
    public ResponseEntity<?> saveOrUpdate(
            @Validated @RequestParam("productName") String productName,
            @RequestParam(value = "imageFile", required = false) MultipartFile productImages,
            @Validated @RequestParam("unitPrice") Double unitPrice,
            @Validated @RequestParam("discount") Double discount,
            @Validated @RequestParam("description") String productDescription,
            @Validated @RequestParam("categoryId") int categoryId,
            @Validated @RequestParam("quantity") Integer quantity,
            @Validated @RequestParam("status") Short status) {

        // Check if product already exists
        Optional<Product> optProduct = productService.findByProductName(productName);
        if (optProduct.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Sản phẩm này đã tồn tại trong hệ thống", optProduct.get()),
                    HttpStatus.BAD_REQUEST);
        }

        // Create new product
        Product product = new Product();
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        product.setDescription(productDescription);
        product.setQuantity(quantity);
        product.setStatus(status);
        product.setCreateDate(new Timestamp(System.currentTimeMillis()));

        // Validate and set category
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (!optCategory.isPresent()) {
            return new ResponseEntity<Response>(
                    new Response(false, "Danh mục không tồn tại", null),
                    HttpStatus.BAD_REQUEST);
        }
        product.setCategory(optCategory.get());

        // Handle file upload
        try {
            if (productImages != null && !productImages.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + productImages.getOriginalFilename();
                storageService.store(productImages, fileName);
                product.setImages(fileName);
            }
        } catch (Exception e) {
            return new ResponseEntity<Response>(
                    new Response(false, "Lỗi khi lưu tệp: " + e.getMessage(), null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save product
        Product savedProduct = productService.save(product);

        return new ResponseEntity<Response>(
                new Response(true, "Thành công", savedProduct),
                HttpStatus.OK);
    }
}
