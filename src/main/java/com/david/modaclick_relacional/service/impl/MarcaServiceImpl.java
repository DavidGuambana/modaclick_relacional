package com.david.modaclick_relacional.service.impl;


import com.david.modaclick_relacional.model.Marca;
import com.david.modaclick_relacional.model.Producto;
import com.david.modaclick_relacional.repository.MarcaRepository;
import com.david.modaclick_relacional.repository.UsuarioRepository;
import com.david.modaclick_relacional.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl  implements MarcaService {

    private final MarcaRepository marcaRepository;


    @Override
    public List<Marca> findAll() {return marcaRepository.findAll();
    }
}
