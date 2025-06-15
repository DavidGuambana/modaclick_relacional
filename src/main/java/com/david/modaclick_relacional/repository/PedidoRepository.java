package com.david.modaclick_relacional.repository;

import com.david.modaclick_relacional.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @EntityGraph(attributePaths = {"detalles"})
    Optional<Pedido> findById(Long id);

    List<Pedido> findPedidoByUsuario_Id(Long usuario_id);
}
