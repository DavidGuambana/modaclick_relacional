package com.david.modaclick_relacional.repository;

import com.david.modaclick_relacional.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
