package com.david.modaclick_relacional.service;

import com.david.modaclick_relacional.model.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();
}