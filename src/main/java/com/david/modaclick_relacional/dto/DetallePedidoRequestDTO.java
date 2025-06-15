package com.david.modaclick_relacional.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class DetallePedidoRequestDTO {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Positive(message = "La cantidad debe ser mayor que cero")
    private double cantidad;

    @PositiveOrZero(message = "El subtotal debe ser mayor o igual a cero")
    private double subtotal;
}