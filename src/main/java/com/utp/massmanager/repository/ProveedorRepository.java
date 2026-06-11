package com.utp.massmanager.repository;

import com.utp.massmanager.model.Proveedor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
  Optional<Proveedor> findByNombre(String nombre);
}