package com.server.api.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.api.dto.LoginDTO;
import com.server.api.config.JwtUtil;
import com.server.api.model.Usuario;
import com.server.api.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
@Tag(name = "Login", description = "Autenticación de inicio de sesión con JWT")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository userRepository;

    @Operation(
            summary = "Iniciar sesión en el sistema",
            description = "Con las credenciales correctas, devuelve un token Bearer para usar en cada solicitud HTTP.",
            tags = {"Login"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario autenticado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, campos no válidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
@PostMapping("/login")
public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO loginDTO) {
    UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDTO.getCorreo(), loginDTO.getPassword());
    try {
        Authentication authentication = authenticationManager.authenticate(login);
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Buscar el usuario autenticado desde los detalles proporcionados por AuthenticationManager
            Optional<Usuario> usuarioOptional = userRepository.findByCorreo(userDetails.getUsername());

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();

                // Crear el token JWT con los detalles necesarios
                String jwt = jwtUtil.create(
                        usuario.getId(),
                        usuario.getCorreo(),
                        usuario.getNombres(),
                        usuario.getHabilitado()
                );

                // Construir la respuesta
                Map<String, String> response = new HashMap<>();
                response.put("token", jwt);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuario no encontrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas"));
    }
}

    @Operation(
            summary = "Obtener usuario actual",
            description = "Devuelve los detalles del usuario actualmente autenticado.",
            tags = {"Usuario"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/actual-usuario")
    public ResponseEntity<Usuario> obtenerUsuarioActual(Principal principal) {
        Optional<Usuario> userEntityOptional = userRepository.findByCorreo(principal.getName());
        return userEntityOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}