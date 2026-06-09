package com.utp.massmanager.service;

import com.utp.massmanager.model.Categoria;
import java.util.List;

public interface CategoriaService {
    List<Categoria> getAllCategorias();
    Categoria getCategoriaById(Long id);
    Categoria createCategoria(Categoria categoria);
    Categoria updateCategoria(Long id, Categoria categoria);
    void deleteCategoria(Long id);
}