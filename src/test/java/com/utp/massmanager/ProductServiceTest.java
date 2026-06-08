package com.utp.massmanager;

import com.utp.massmanager.model.Product;
import com.utp.massmanager.repository.ProductRepository;
import com.utp.massmanager.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    // Se inyecta sobre la implementacion real del servicio
    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setName("Gaseosa Inka Cola 3L");
        sampleProduct.setPrice(11.50);
        sampleProduct.setStock(50);
    }

    @Test
    void testGetProductById_Success() {
        // Simular que el repositorio encuentra el producto
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        // Ejecutar el metodo
        Product foundProduct = productService.getProductById(1L);

        // Validaciones
        assertNotNull(foundProduct);
        assertEquals("Gaseosa Inka Cola 3L", foundProduct.getName());
        assertEquals(11.50, foundProduct.getPrice());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct_Success() {
        // Simular el guardado
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        // Ejecutar
        Product savedProduct = productService.createProduct(sampleProduct);

        // Validaciones
        assertNotNull(savedProduct);
        assertEquals(1L, savedProduct.getId());
        verify(productRepository, times(1)).save(sampleProduct);
    }
}
