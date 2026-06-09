package com.utp.massmanager.controller;

import com.utp.massmanager.model.Categoria;
import com.utp.massmanager.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired private CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> getAll() { return categoriaService.getAllCategorias(); }

    @GetMapping("/{id}")
    public Categoria getById(@PathVariable Long id) { return categoriaService.getCategoriaById(id); }

    @PostMapping
    public Categoria create(@RequestBody Categoria categoria) { return categoriaService.createCategoria(categoria); }

    @PutMapping("/{id}")
    public Categoria update(@PathVariable Long id, @RequestBody Categoria categoria) { return categoriaService.updateCategoria(id, categoria); }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
        return "Categoria eliminada con ID: " + id;
    }
}