package com.david.modaclick_relacional.service;

import com.david.modaclick_relacional.dto.LoginRequestDTO;
import com.david.modaclick_relacional.dto.LoginResponseDTO;
import com.david.modaclick_relacional.dto.RegisterRequestDTO;
import com.david.modaclick_relacional.model.Usuario;

import java.util.List;

public interface UsuarioService {
    LoginResponseDTO register(RegisterRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);

    Usuario findByUsername(String username);

    List<Usuario> findAll();

    void updatePassword(Usuario usuario);
}
