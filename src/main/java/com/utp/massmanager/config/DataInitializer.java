package com.utp.massmanager.config;

import com.utp.massmanager.model.Empleado;
import com.utp.massmanager.model.Rol;
import com.utp.massmanager.repository.EmpleadoRepository;
import com.utp.massmanager.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private RolRepository rolRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // ── ROLES ──────────────────────────────────────────
        Rol admin    = getOrCreateRol("Administrador");
        Rol cajero   = getOrCreateRol("Cajero");
        Rol almacen  = getOrCreateRol("Almacén");

        // ── EMPLEADOS ──────────────────────────────────────
        createEmpleado("Carlos",  "Ramírez", "admin@mass.pe",   "admin123",   admin);
        createEmpleado("Lucía",   "Torres",  "cajero@mass.pe",  "cajero123",  cajero);
        createEmpleado("Miguel",  "Flores",  "almacen@mass.pe", "almacen123", almacen);
    }

    private Rol getOrCreateRol(String descripcion) {
        return rolRepository.findByDescripcion(descripcion)
                .orElseGet(() -> {
                    Rol rol = new Rol();
                    rol.setDescripcion(descripcion);
                    return rolRepository.save(rol);
                });
    }

    private void createEmpleado(String nombre, String apellido, String correo, String password, Rol rol) {
        if (empleadoRepository.findByCorreo(correo).isEmpty()) {
            Empleado e = new Empleado();
            e.setNombre(nombre);
            e.setApellido(apellido);
            e.setCorreo(correo);
            e.setPassword(passwordEncoder.encode(password));
            e.setRol(rol);
            empleadoRepository.save(e);
        }
    }
}