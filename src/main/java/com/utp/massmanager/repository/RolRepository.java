package com.utp.massmanager.repository;

import com.utp.massmanager.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByDescripcion(String descripcion);
}