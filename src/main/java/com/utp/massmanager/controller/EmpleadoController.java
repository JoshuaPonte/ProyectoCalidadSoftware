package com.utp.massmanager.controller;

import com.utp.massmanager.model.Empleado;
import com.utp.massmanager.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired private EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> getAll() { return empleadoService.getAllEmpleados(); }

    @GetMapping("/{id}")
    public Empleado getById(@PathVariable Long id) { return empleadoService.getEmpleadoById(id); }

    @PostMapping
    public Empleado create(@RequestBody Empleado empleado) { return empleadoService.createEmpleado(empleado); }

    @PutMapping("/{id}")
    public Empleado update(@PathVariable Long id, @RequestBody Empleado empleado) { return empleadoService.updateEmpleado(id, empleado); }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        empleadoService.deleteEmpleado(id);
        return "Empleado eliminado con ID: " + id;
    }
}