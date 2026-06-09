package com.utp.massmanager.service;

import com.utp.massmanager.model.Proveedor;
import com.utp.massmanager.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    @Override
    public List<Proveedor> getAllProveedores() { return repository.findAll(); }

    @Override
    public Proveedor getProveedorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

    @Override
    public Proveedor createProveedor(Proveedor proveedor) { return repository.save(proveedor); }

    @Override
    public Proveedor updateProveedor(Long id, Proveedor proveedorDetails) {
        Proveedor proveedor = getProveedorById(id);
        proveedor.setNombre(proveedorDetails.getNombre());
        proveedor.setTelefono(proveedorDetails.getTelefono());
        proveedor.setDireccion(proveedorDetails.getDireccion());
        return repository.save(proveedor);
    }

    @Override
    public void deleteProveedor(Long id) { repository.delete(getProveedorById(id)); }
}