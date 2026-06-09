package com.utp.massmanager.repository;

import com.utp.massmanager.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {}