package com.david.modaclick_relacional.controller;


import com.david.modaclick_relacional.dto.LoginRequestDTO;
import com.david.modaclick_relacional.dto.LoginResponseDTO;
import com.david.modaclick_relacional.dto.RegisterRequestDTO;
import com.david.modaclick_relacional.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = usuarioService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO request) {
        System.out.println("Intentando registrar usuario: " + request.getUsername());

        try {
            LoginResponseDTO response = usuarioService.register(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            System.out.println("❌ Error de validación: " + ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            System.out.println("🔥 Error inesperado: " + ex.getMessage());
            ex.printStackTrace(); // Imprime el stack completo en consola
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + ex.getMessage()); // Opcional mostrar el error real al cliente
        }
    }

}
