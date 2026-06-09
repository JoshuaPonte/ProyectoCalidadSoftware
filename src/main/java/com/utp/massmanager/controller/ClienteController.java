package com.utp.massmanager.controller;

import com.utp.massmanager.model.Cliente;
import com.utp.massmanager.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAll() { return clienteService.getAllClientes(); }

    @GetMapping("/{id}")
    public Cliente getById(@PathVariable Long id) { return clienteService.getClienteById(id); }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) { return clienteService.createCliente(cliente); }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody Cliente cliente) { return clienteService.updateCliente(id, cliente); }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return "Cliente eliminado con ID: " + id;
    }
}