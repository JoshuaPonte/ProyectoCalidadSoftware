package com.utp.massmanager.controller;

import com.utp.massmanager.model.Proveedor;
import com.utp.massmanager.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired private ProveedorService proveedorService;

    @GetMapping
    public List<Proveedor> getAll() { return proveedorService.getAllProveedores(); }

    @GetMapping("/{id}")
    public Proveedor getById(@PathVariable Long id) { return proveedorService.getProveedorById(id); }

    @PostMapping
    public Proveedor create(@RequestBody Proveedor proveedor) { return proveedorService.createProveedor(proveedor); }

    @PutMapping("/{id}")
    public Proveedor update(@PathVariable Long id, @RequestBody Proveedor proveedor) { return proveedorService.updateProveedor(id, proveedor); }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        proveedorService.deleteProveedor(id);
        return "Proveedor eliminado con ID: " + id;
    }
}