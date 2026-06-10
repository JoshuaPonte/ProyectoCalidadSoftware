package com.utp.massmanager.repository;

import com.utp.massmanager.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByCorreo(String correo);
}