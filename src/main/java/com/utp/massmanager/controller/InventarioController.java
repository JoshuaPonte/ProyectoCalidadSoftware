package com.utp.massmanager.controller;

import com.utp.massmanager.model.Inventario;
import com.utp.massmanager.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> getAll() { return inventarioService.getAllInventario(); }

    @GetMapping("/{id}")
    public Inventario getById(@PathVariable Long id) { return inventarioService.getInventarioById(id); }

    @GetMapping("/producto/{productoId}")
    public Inventario getByProducto(@PathVariable Long productoId) {
        return inventarioService.getInventarioByProductoId(productoId);
    }

    @PostMapping("/entrada")
    public Inventario entrada(@RequestBody Map<String, Object> body) {
        Long productoId = Long.valueOf(body.get("productoId").toString());
        Integer cantidad = Integer.valueOf(body.get("cantidad").toString());
        Long empleadoId = Long.valueOf(body.get("empleadoId").toString());
        String justificacion = body.getOrDefault("justificacion", "").toString();
        return inventarioService.registrarEntrada(productoId, cantidad, empleadoId, justificacion);
    }

    @PostMapping("/salida")
    public Inventario salida(@RequestBody Map<String, Object> body) {
        Long productoId = Long.valueOf(body.get("productoId").toString());
        Integer cantidad = Integer.valueOf(body.get("cantidad").toString());
        Long empleadoId = Long.valueOf(body.get("empleadoId").toString());
        String justificacion = body.getOrDefault("justificacion", "").toString();
        return inventarioService.registrarSalida(productoId, cantidad, empleadoId, justificacion);
    }
}