package com.utp.massmanager.service;

import com.utp.massmanager.model.Product;
import com.utp.massmanager.model.ProductRequest;
import com.utp.massmanager.repository.CategoriaRepository;
import com.utp.massmanager.repository.ProductRepository;
import com.utp.massmanager.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired private ProductRepository repository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private ProveedorRepository proveedorRepository;

    private static final String UPLOAD_DIR = "uploads/productos/";

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
    public Product createProduct(ProductRequest request, MultipartFile imagen) {
        Product product = new Product();
        product.setNombre(request.getNombre());
        product.setPrecio(request.getPrecio());
        product.setStock(request.getStock());
        product.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 0);

        product.setCategoria(categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));

        if (request.getIdProveedor() != null) {
            product.setProveedor(proveedorRepository.findById(request.getIdProveedor())
                    .orElse(null));
        }

        if (imagen != null && !imagen.isEmpty()) {
            product.setImagenUrl(guardarImagen(imagen));
        }

        return repository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductRequest request, MultipartFile imagen) {
        Product product = getProductById(id);
        product.setNombre(request.getNombre());
        product.setPrecio(request.getPrecio());
        product.setStock(request.getStock());
        product.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 0);

        product.setCategoria(categoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada")));

        if (request.getIdProveedor() != null) {
            product.setProveedor(proveedorRepository.findById(request.getIdProveedor())
                    .orElse(null));
        }

        if (imagen != null && !imagen.isEmpty()) {
            product.setImagenUrl(guardarImagen(imagen));
        }

        return repository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        repository.delete(product);
    }

    private String guardarImagen(MultipartFile imagen) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
            String filename = java.util.UUID.randomUUID() + "_" + imagen.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(imagen.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/" + UPLOAD_DIR + filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imagen: " + e.getMessage());
        }
    }
}