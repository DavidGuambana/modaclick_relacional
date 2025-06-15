package com.david.modaclick_relacional.service;

import com.david.modaclick_relacional.dto.ProductoRequestDTO;
import com.david.modaclick_relacional.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto save(ProductoRequestDTO producto);
    Optional<Producto> findById(Long id);
    List<Producto> findAll();
    void deleteById(Long id);
}