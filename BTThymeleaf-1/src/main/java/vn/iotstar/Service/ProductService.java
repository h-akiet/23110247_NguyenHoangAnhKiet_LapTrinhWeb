package vn.iotstar.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.iotstar.Entity.Product;
import vn.iotstar.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products from the database.
     * 
     * @return List of all Product entities
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Finds a product by its name.
     * 
     * @param productName The name of the product to search for
     * @return Optional containing the Product if found, or empty if not found
     */
    public Optional<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    /**
     * Saves a product to the database.
     * 
     * @param product The Product entity to save
     * @return The saved Product entity
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Finds a product by its ID.
     * 
     * @param id The ID of the product to search for
     * @return Optional containing the Product if found, or empty if not found
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id The ID of the product to delete
     */
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}