package com.utp.massmanager.service;

import com.utp.massmanager.model.*;
import com.utp.massmanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private DetalleVentaRepository detalleRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private InventarioRepository inventarioRepository;
    @Autowired private MovimientoInventarioRepository movimientoRepository;

    @Override
    public List<Venta> getAllVentas() { return ventaRepository.findAll(); }

    @Override
    public Venta getVentaById(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public Venta registrarVenta(Long empleadoId, Long clienteId, List<DetalleVenta> detalles) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        Venta venta = new Venta();
        venta.setEmpleado(empleado);

        if (clienteId != null) {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
            venta.setCliente(cliente);
        }

        double total = 0.0;

        for (DetalleVenta detalle : detalles) {
            Product producto = productRepository.findById(detalle.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            // Descontar stock
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productRepository.save(producto);

            // Actualizar inventario
            inventarioRepository.findByProducto(producto).ifPresent(inv -> {
                inv.setCantidad(producto.getStock());
                inventarioRepository.save(inv);
            });

            // Registrar movimiento
            MovimientoInventario movimiento = new MovimientoInventario();
            movimiento.setProducto(producto);
            movimiento.setEmpleado(empleado);
            movimiento.setTipoMovimiento(MovimientoInventario.TipoMovimiento.VENTA);
            movimiento.setCantidad(detalle.getCantidad());
            movimientoRepository.save(movimiento);

            double subtotal = producto.getPrecio() * detalle.getCantidad();
            detalle.setSubtotal(subtotal);
            detalle.setVenta(venta);
            detalle.setProducto(producto);
            total += subtotal;
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);
        return ventaRepository.save(venta);
    }
}