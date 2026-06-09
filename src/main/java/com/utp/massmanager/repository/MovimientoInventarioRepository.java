package com.utp.massmanager.repository;

import com.utp.massmanager.model.MovimientoInventario;
import com.utp.massmanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findByProductoOrderByCreatedAtDesc(Product producto);
}