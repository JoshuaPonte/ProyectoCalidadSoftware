package com.utp.massmanager.service;

import com.utp.massmanager.model.Empleado;
import java.util.List;

public interface EmpleadoService {
    List<Empleado> getAllEmpleados();
    Empleado getEmpleadoById(Long id);
    Empleado createEmpleado(Empleado empleado);
    Empleado updateEmpleado(Long id, Empleado empleado);
    void deleteEmpleado(Long id);
}