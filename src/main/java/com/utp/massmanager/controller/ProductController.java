package com.utp.massmanager.controller;

import com.utp.massmanager.model.Product;
import com.utp.massmanager.model.ProductRequest;
import com.utp.massmanager.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product createProduct(
            @RequestPart("data") ProductRequest request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return productService.createProduct(request, imagen);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product updateProduct(
            @PathVariable Long id,
            @RequestPart("data") ProductRequest request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {
        return productService.updateProduct(id, request, imagen);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully with ID: " + id;
    }
}
