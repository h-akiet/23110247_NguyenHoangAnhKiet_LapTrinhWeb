package vn.iotstar.Service;

import vn.iotstar.Entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import org.springframework.data.domain.Example;


import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Page<Category> findByCategoryNameContaining(String name, Pageable pageable);
    Optional<Category> findById(int categoryId);
    Optional<Category> findByCategoryName(String name);

    
    void deleteById(int id);
    void delete(Category entity);
    long count();
    <S extends Category> Optional<S> findOne(Example<S> example);
    List<Category> findAll();
    List<Category> findByCategoryNameContaining(String name);
    List<Category> findAllById(Iterable<Integer> ids);
    List<Category> findAll(Sort sort);
    <S extends Category> S save(S entity);
    
}
