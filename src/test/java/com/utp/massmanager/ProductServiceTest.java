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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setNombre("Arroz 1kg");
        sampleProduct.setPrecio(3.50);
        sampleProduct.setStock(100);
        sampleProduct.setCodigo("AR-001");
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        Product foundProduct = productService.getProductById(1L);
        assertEquals("Arroz 1kg", foundProduct.getNombre());
        assertEquals(3.50, foundProduct.getPrecio());
    }
}