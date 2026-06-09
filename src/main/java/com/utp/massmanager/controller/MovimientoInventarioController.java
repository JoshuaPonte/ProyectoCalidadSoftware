package com.utp.massmanager.controller;

import com.utp.massmanager.model.MovimientoInventario;
import com.utp.massmanager.service.MovimientoInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientoInventarioController {

    @Autowired private MovimientoInventarioService movimientoService;

    @GetMapping
    public List<MovimientoInventario> getAll() { return movimientoService.getAllMovimientos(); }

    @GetMapping("/{id}")
    public MovimientoInventario getById(@PathVariable Long id) { return movimientoService.getMovimientoById(id); }

    @GetMapping("/producto/{productoId}")
    public List<MovimientoInventario> getByProducto(@PathVariable Long productoId) {
        return movimientoService.getMovimientosByProducto(productoId);
    }
}