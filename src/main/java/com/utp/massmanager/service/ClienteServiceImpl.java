package com.utp.massmanager.service;

import com.utp.massmanager.model.Cliente;
import com.utp.massmanager.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public List<Cliente> getAllClientes() { return repository.findAll(); }

    @Override
    public Cliente getClienteById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public Cliente createCliente(Cliente cliente) { return repository.save(cliente); }

    @Override
    public Cliente updateCliente(Long id, Cliente clienteDetails) {
        Cliente cliente = getClienteById(id);
        cliente.setNombre(clienteDetails.getNombre());
        cliente.setTelefono(clienteDetails.getTelefono());
        cliente.setCorreo(clienteDetails.getCorreo());
        return repository.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) { repository.delete(getClienteById(id)); }
}