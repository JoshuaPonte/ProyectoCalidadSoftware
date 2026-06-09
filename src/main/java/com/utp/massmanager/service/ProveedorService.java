package com.utp.massmanager.service;

import com.utp.massmanager.model.Proveedor;
import java.util.List;

public interface ProveedorService {
    List<Proveedor> getAllProveedores();
    Proveedor getProveedorById(Long id);
    Proveedor createProveedor(Proveedor proveedor);
    Proveedor updateProveedor(Long id, Proveedor proveedor);
    void deleteProveedor(Long id);
}