package com.utp.massmanager.service;

import com.utp.massmanager.model.Product;
import com.utp.massmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setCodigo(productDetails.getCodigo());
        product.setNombre(productDetails.getNombre());
        product.setPrecio(productDetails.getPrecio());
        product.setStock(productDetails.getStock());
        product.setStockMinimo(productDetails.getStockMinimo());
        product.setCategoria(productDetails.getCategoria());
        product.setProveedor(productDetails.getProveedor());
        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        repository.delete(product);
    }
}