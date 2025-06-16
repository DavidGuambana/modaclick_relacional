package com.david.modaclick_relacional.service.impl;

import com.david.modaclick_relacional.dto.ProductoRequestDTO;
import com.david.modaclick_relacional.model.Categoria;
import com.david.modaclick_relacional.model.Marca;
import com.david.modaclick_relacional.model.Producto;
import com.david.modaclick_relacional.repository.CategoriaRepository;
import com.david.modaclick_relacional.repository.MarcaRepository;
import com.david.modaclick_relacional.repository.ProductoRepository;
import com.david.modaclick_relacional.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MarcaRepository marcaRepository;

    @Override
    public Producto save(ProductoRequestDTO productoDTO, String idImg) {
        // Buscar la categoría y marca, lanzar excepción si no existen
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + productoDTO.getCategoriaId()));

        Marca marca = marcaRepository.findById(productoDTO.getMarcaId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + productoDTO.getMarcaId()));

        // Crear nuevo producto con datos del DTO y las entidades relacionadas
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStock(productoDTO.getStock());
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setId_imagen(idImg);

        // Guardar y retornar
        return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return productoRepository.findById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}
