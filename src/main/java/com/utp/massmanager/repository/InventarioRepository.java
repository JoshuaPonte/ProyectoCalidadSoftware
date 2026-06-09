package com.utp.massmanager.repository;

import com.utp.massmanager.model.Inventario;
import com.utp.massmanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findByProducto(Product producto);
}