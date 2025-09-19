package vn.iotstar.Repository;

import vn.iotstar.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Tìm kiếm theo tên danh mục
    List<Category> findByCategoryNameContaining(String name);

    // Tìm kiếm và Phân trang theo tên danh mục
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
    
    Page<Category> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);
    List<Category> findByCategoryNameContainingIgnoreCase(String name);
   
    
   
}