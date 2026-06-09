package com.utp.massmanager.service;

import com.utp.massmanager.model.MovimientoInventario;
import com.utp.massmanager.model.Product;
import com.utp.massmanager.repository.MovimientoInventarioRepository;
import com.utp.massmanager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    @Autowired private MovimientoInventarioRepository repository;
    @Autowired private ProductRepository productRepository;

    @Override
    public List<MovimientoInventario> getAllMovimientos() { return repository.findAll(); }

    @Override
    public List<MovimientoInventario> getMovimientosByProducto(Long productoId) {
        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
        return repository.findByProductoOrderByCreatedAtDesc(producto);
    }

    @Override
    public MovimientoInventario getMovimientoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + id));
    }
}