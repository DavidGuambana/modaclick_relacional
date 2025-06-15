package com.david.modaclick_relacional.data;

import com.david.modaclick_relacional.model.Usuario;
import com.david.modaclick_relacional.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Inicializer implements CommandLineRunner {

    private final UsuarioService usuarioService;

    @Override
    public void run(String... args) throws Exception {
        // Actualizar el password hasheado de todos los usuarios
        List<Usuario> usuarios = usuarioService.findAll();
        for (Usuario usuario : usuarios) {
            usuarioService.updatePassword(usuario);
        }
    }
}

