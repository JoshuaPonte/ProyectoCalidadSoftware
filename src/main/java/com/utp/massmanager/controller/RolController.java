package com.utp.massmanager.controller;

import com.utp.massmanager.model.Rol;
import com.utp.massmanager.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired private RolService rolService;

    @GetMapping
    public List<Rol> getAll() { return rolService.getAllRoles(); }

    @GetMapping("/{id}")
    public Rol getById(@PathVariable Long id) { return rolService.getRolById(id); }

    @PostMapping
    public Rol create(@RequestBody Rol rol) { return rolService.createRol(rol); }

    @PutMapping("/{id}")
    public Rol update(@PathVariable Long id, @RequestBody Rol rol) { return rolService.updateRol(id, rol); }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        rolService.deleteRol(id);
        return "Rol eliminado con ID: " + id;
    }
}