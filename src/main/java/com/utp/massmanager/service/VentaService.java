package com.utp.massmanager.service;

import com.utp.massmanager.model.Venta;
import com.utp.massmanager.model.DetalleVenta;
import java.util.List;

public interface VentaService {
    List<Venta> getAllVentas();
    Venta getVentaById(Long id);
    Venta registrarVenta(Long empleadoId, Long clienteId, List<DetalleVenta> detalles);
}