package com.utp.massmanager.service;

import com.utp.massmanager.model.Rol;
import java.util.List;

public interface RolService {
    List<Rol> getAllRoles();
    Rol getRolById(Long id);
    Rol createRol(Rol rol);
    Rol updateRol(Long id, Rol rol);
    void deleteRol(Long id);
}