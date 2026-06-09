package com.utp.massmanager.service;

import com.utp.massmanager.model.Categoria;
import com.utp.massmanager.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Override
    public List<Categoria> getAllCategorias() { return repository.findAll(); }

    @Override
    public Categoria getCategoriaById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
    }

    @Override
    public Categoria createCategoria(Categoria categoria) { return repository.save(categoria); }

    @Override
    public Categoria updateCategoria(Long id, Categoria categoriaDetails) {
        Categoria categoria = getCategoriaById(id);
        categoria.setNombre(categoriaDetails.getNombre());
        categoria.setDescripcion(categoriaDetails.getDescripcion());
        return repository.save(categoria);
    }

    @Override
    public void deleteCategoria(Long id) { repository.delete(getCategoriaById(id)); }
}