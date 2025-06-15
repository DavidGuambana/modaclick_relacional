package com.david.modaclick_relacional.repository;

import com.david.modaclick_relacional.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
}