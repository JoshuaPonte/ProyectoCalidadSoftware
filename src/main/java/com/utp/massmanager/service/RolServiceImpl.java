package com.utp.massmanager.service;

import com.utp.massmanager.model.Rol;
import com.utp.massmanager.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository repository;

    @Override
    public List<Rol> getAllRoles() { return repository.findAll(); }

    @Override
    public Rol getRolById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));
    }

    @Override
    public Rol createRol(Rol rol) { return repository.save(rol); }

    @Override
    public Rol updateRol(Long id, Rol rolDetails) {
        Rol rol = getRolById(id);
        rol.setDescripcion(rolDetails.getDescripcion());
        return repository.save(rol);
    }

    @Override
    public void deleteRol(Long id) { repository.delete(getRolById(id)); }
}