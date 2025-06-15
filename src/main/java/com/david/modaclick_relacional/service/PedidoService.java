package com.david.modaclick_relacional.service;

import com.david.modaclick_relacional.dto.PedidoRequestDTO;
import com.david.modaclick_relacional.model.Pedido;
import com.david.modaclick_relacional.model.Producto;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    Pedido save(PedidoRequestDTO pedido);
    List<Pedido> findAll();
    void deleteById(Long id);
    List<Pedido> findByUsuarioId(Long userid);
}