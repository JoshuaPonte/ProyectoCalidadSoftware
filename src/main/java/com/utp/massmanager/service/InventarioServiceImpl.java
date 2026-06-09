package com.utp.massmanager.service;

import com.utp.massmanager.model.*;
import com.utp.massmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired private InventarioRepository inventarioRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private MovimientoInventarioRepository movimientoRepository;

    @Override
    public List<Inventario> getAllInventario() { return inventarioRepository.findAll(); }

    @Override
    public Inventario getInventarioById(Long id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    @Override
    public Inventario getInventarioByProductoId(Long productoId) {
        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
        return inventarioRepository.findByProducto(producto)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para el producto: " + productoId));
    }

    @Override
    @Transactional
    public Inventario registrarEntrada(Long productoId, Integer cantidad, Long empleadoId, String justificacion) {
        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        // Actualizar stock del producto
        producto.setStock(producto.getStock() + cantidad);
        productRepository.save(producto);

        // Actualizar inventario
        Inventario inventario = inventarioRepository.findByProducto(producto)
                .orElse(new Inventario());
        inventario.setProducto(producto);
        inventario.setCantidad(producto.getStock());
        inventarioRepository.save(inventario);

        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setEmpleado(empleado);
        movimiento.setTipoMovimiento(MovimientoInventario.TipoMovimiento.ENTRADA);
        movimiento.setCantidad(cantidad);
        movimiento.setJustificacion(justificacion);
        movimientoRepository.save(movimiento);

        return inventario;
    }

    @Override
    @Transactional
    public Inventario registrarSalida(Long productoId, Integer cantidad, Long empleadoId, String justificacion) {
        Product producto = productRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        if (producto.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStock());
        }

        // Actualizar stock
        producto.setStock(producto.getStock() - cantidad);
        productRepository.save(producto);

        // Actualizar inventario
        Inventario inventario = inventarioRepository.findByProducto(producto)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        inventario.setCantidad(producto.getStock());
        inventarioRepository.save(inventario);

        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setProducto(producto);
        movimiento.setEmpleado(empleado);
        movimiento.setTipoMovimiento(MovimientoInventario.TipoMovimiento.SALIDA);
        movimiento.setCantidad(cantidad);
        movimiento.setJustificacion(justificacion);
        movimientoRepository.save(movimiento);

        return inventario;
    }
}