package com.utp.massmanager.controller;

import com.utp.massmanager.model.DetalleVenta;
import com.utp.massmanager.model.Product;
import com.utp.massmanager.model.Venta;
import com.utp.massmanager.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired private VentaService ventaService;

    @GetMapping
    public List<Venta> getAll() { return ventaService.getAllVentas(); }

    @GetMapping("/{id}")
    public Venta getById(@PathVariable Long id) { return ventaService.getVentaById(id); }

    @PostMapping
    public Venta registrar(@RequestBody Map<String, Object> body) {
        Long empleadoId = Long.valueOf(body.get("empleadoId").toString());
        Long clienteId = body.get("clienteId") != null ? Long.valueOf(body.get("clienteId").toString()) : null;

        List<Map<String, Object>> detallesData = (List<Map<String, Object>>) body.get("detalles");
        List<DetalleVenta> detalles = new ArrayList<>();

        for (Map<String, Object> d : detallesData) {
            DetalleVenta detalle = new DetalleVenta();
            Product p = new Product();
            p.setId(Long.valueOf(d.get("productoId").toString()));
            detalle.setProducto(p);
            detalle.setCantidad(Integer.valueOf(d.get("cantidad").toString()));
            detalles.add(detalle);
        }

        return ventaService.registrarVenta(empleadoId, clienteId, detalles);
    }
}