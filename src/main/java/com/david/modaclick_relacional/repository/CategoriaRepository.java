package com.david.modaclick_relacional.repository;

import com.david.modaclick_relacional.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
