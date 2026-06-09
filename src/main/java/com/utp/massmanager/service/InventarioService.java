package com.utp.massmanager.service;

import com.utp.massmanager.model.Inventario;
import java.util.List;

public interface InventarioService {
    List<Inventario> getAllInventario();
    Inventario getInventarioById(Long id);
    Inventario getInventarioByProductoId(Long productoId);
    Inventario registrarEntrada(Long productoId, Integer cantidad, Long empleadoId, String justificacion);
    Inventario registrarSalida(Long productoId, Integer cantidad, Long empleadoId, String justificacion);
}