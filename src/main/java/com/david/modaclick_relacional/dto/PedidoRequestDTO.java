package com.david.modaclick_relacional.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    private double total;

    private boolean pagado;

    @NotEmpty(message = "Debe contener al menos un detalle en el pedido")
    private List<@Valid DetallePedidoRequestDTO> detalles;
}