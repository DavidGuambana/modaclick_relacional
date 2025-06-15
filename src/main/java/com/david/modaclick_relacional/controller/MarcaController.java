package com.david.modaclick_relacional.controller;


import com.david.modaclick_relacional.model.Marca;
import com.david.modaclick_relacional.model.Producto;
import com.david.modaclick_relacional.service.MarcaService;
import com.david.modaclick_relacional.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> listarmarcas() {
        return ResponseEntity.ok(marcaService.findAll());
    }
}