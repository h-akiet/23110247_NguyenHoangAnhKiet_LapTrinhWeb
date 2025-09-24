package vn.iotstar.Repository;

import org.springframework.data.domain.Pageable;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import vn.iotstar.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByProductNameContaining(String name);
	
	Page <Product> findByProductNameContaining(String name,Pageable pageable);
	Optional<Product> findByProductName(String name);
	Optional<Product> findByCreateDate(Date createAt);

}
