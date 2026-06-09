package com.utp.massmanager.service;

import com.utp.massmanager.model.MovimientoInventario;
import java.util.List;

public interface MovimientoInventarioService {
    List<MovimientoInventario> getAllMovimientos();
    List<MovimientoInventario> getMovimientosByProducto(Long productoId);
    MovimientoInventario getMovimientoById(Long id);
}