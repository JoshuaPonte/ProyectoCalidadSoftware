package com.utp.massmanager.service;

import com.utp.massmanager.model.Empleado;
import com.utp.massmanager.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository repository;

    @Override
    public List<Empleado> getAllEmpleados() { return repository.findAll(); }

    @Override
    public Empleado getEmpleadoById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con ID: " + id));
    }

    @Override
    public Empleado createEmpleado(Empleado empleado) { return repository.save(empleado); }

    @Override
    public Empleado updateEmpleado(Long id, Empleado empleadoDetails) {
        Empleado empleado = getEmpleadoById(id);
        empleado.setNombre(empleadoDetails.getNombre());
        empleado.setApellido(empleadoDetails.getApellido());
        empleado.setCorreo(empleadoDetails.getCorreo());
        empleado.setRol(empleadoDetails.getRol());
        return repository.save(empleado);
    }

    @Override
    public void deleteEmpleado(Long id) { repository.delete(getEmpleadoById(id)); }
}