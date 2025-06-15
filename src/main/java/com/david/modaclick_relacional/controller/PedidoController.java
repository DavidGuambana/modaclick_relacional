package com.david.modaclick_relacional.controller;

import com.david.modaclick_relacional.dto.PedidoRequestDTO;
import com.david.modaclick_relacional.model.Pedido;
import com.david.modaclick_relacional.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody PedidoRequestDTO pedidoDTO) {
        Pedido pedido = pedidoService.save(pedidoDTO);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Pedido>> listarPedidosPorUsuario(@PathVariable("id") Long usuarioId) {
        List<Pedido> pedidos = pedidoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(pedidos);
    }
}
