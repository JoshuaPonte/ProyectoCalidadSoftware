package com.utp.massmanager.auth;

import com.utp.massmanager.model.Empleado;
import com.utp.massmanager.model.Rol;
import com.utp.massmanager.repository.EmpleadoRepository;
import com.utp.massmanager.repository.RolRepository;
import com.utp.massmanager.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private RolRepository rolRepository;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        Empleado empleado = empleadoRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), empleado.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(empleado.getId(), empleado.getCorreo(), empleado.getRol().getDescripcion());
        return new AuthResponse(token, empleado.getCorreo(), empleado.getNombre(), empleado.getRol().getDescripcion());
    }

    public AuthResponse register(RegisterRequest request) {
        if (empleadoRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        Rol rol = rolRepository.findById(request.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Empleado empleado = new Empleado();
        empleado.setNombre(request.getNombre());
        empleado.setApellido(request.getApellido());
        empleado.setCorreo(request.getCorreo());
        empleado.setPassword(passwordEncoder.encode(request.getPassword()));
        empleado.setRol(rol);

        empleadoRepository.save(empleado);

        String token = jwtUtil.generateToken(empleado.getId(), empleado.getCorreo(), rol.getDescripcion());
        return new AuthResponse(token, empleado.getCorreo(), empleado.getNombre(), rol.getDescripcion());
    }
}