package com.david.modaclick_relacional.dto;

public record LoginResponseDTO(
        String token,
        String nombre_completo,
        Long id_usuario
) { }