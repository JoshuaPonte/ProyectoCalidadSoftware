package com.utp.massmanager.service;

import com.utp.massmanager.model.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> getAllClientes();
    Cliente getClienteById(Long id);
    Cliente createCliente(Cliente cliente);
    Cliente updateCliente(Long id, Cliente cliente);
    void deleteCliente(Long id);
}