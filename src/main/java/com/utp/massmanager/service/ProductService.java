package com.utp.massmanager.service;

import com.utp.massmanager.model.Product;
import com.utp.massmanager.model.ProductRequest;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Product createProduct(ProductRequest request, MultipartFile imagen);
    Product updateProduct(Long id, ProductRequest request, MultipartFile imagen);
}
