package com.david.modaclick_relacional.service.impl;

import com.david.modaclick_relacional.dto.LoginRequestDTO;
import com.david.modaclick_relacional.dto.LoginResponseDTO;
import com.david.modaclick_relacional.dto.RegisterRequestDTO;
import com.david.modaclick_relacional.model.Rol;
import com.david.modaclick_relacional.model.Usuario;
import com.david.modaclick_relacional.repository.RolRepository;
import com.david.modaclick_relacional.repository.UsuarioRepository;
import com.david.modaclick_relacional.security.JwtUtil;
import com.david.modaclick_relacional.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    private final RolRepository rolRepository;

    @Override
    public LoginResponseDTO register(RegisterRequestDTO request) {
        // Validar si el username ya existe
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario '" + request.getUsername() + "' ya fue seleccionado.");
        }

        // Crear entidad Usuario desde el DTO
        Usuario user = new Usuario();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setTelefono(request.getTelefono());
        user.setEmail(request.getEmail());
        user.setDireccion(request.getDireccion());

        // Asignar rol con ID 1 (por ejemplo: "CLIENTE")
        Rol rol = rolRepository.findById(1L).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.setRol(rol);

        // Guardar usuario
        userRepository.save(user);

        // Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return new LoginResponseDTO(token, user.getNombre() + " " + user.getApellido());
    }


    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales inválidas");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        Usuario usuario = findByUsername(userDetails.getUsername());

        return new LoginResponseDTO(token, usuario.getNombre() + " " + usuario.getApellido());
    }

    @Override
    public Usuario findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<Usuario> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void updatePassword(Usuario user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
