package com.david.modaclick_relacional.repository;

import com.david.modaclick_relacional.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
