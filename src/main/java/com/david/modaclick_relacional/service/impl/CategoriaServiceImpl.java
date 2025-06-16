package com.david.modaclick_relacional.service.impl;


import com.david.modaclick_relacional.model.Categoria;
import com.david.modaclick_relacional.repository.CategoriaRepository;
import com.david.modaclick_relacional.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;


    @Override
    public List<Categoria> findAll() {return categoriaRepository.findAll();
    }
}
