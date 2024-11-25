package com.server.api.controller;

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

    // Mapa en memoria para rastrear los intentos fallidos
    private final Map<String, Integer> loginAttempts = new HashMap<>();

    private static final int MAX_ATTEMPTS = 3;

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
        String correo = loginDTO.getCorreo();

        // Verificar si el usuario está en la base de datos
        Optional<Usuario> usuarioOptional = userRepository.findByCorreo(correo);

        if (usuarioOptional.isEmpty()) {
            // Si el usuario no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "El correo electrónico no está registrado."));
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar si el usuario está bloqueado (no habilitado)
        if (!usuario.getHabilitado()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario bloqueado, por favor contacte al soporte para desbloquear la cuenta."));
        }

        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(correo, loginDTO.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(login);
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                // Restablecer intentos fallidos si la autenticación es exitosa
                loginAttempts.remove(correo);

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (BadCredentialsException e) {
            // Incrementar intentos fallidos
            int attempts = loginAttempts.getOrDefault(correo, 0);
            attempts++;
            loginAttempts.put(correo, attempts);

            // Bloquear al usuario después del cuarto intento fallido
            if (attempts >= MAX_ATTEMPTS) {
                usuario.setHabilitado(false);  // Actualizar el estado en la base de datos
                userRepository.save(usuario); // Guardar el cambio en la base de datos

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Usuario bloqueado, por favor contacte al soporte para desbloquear la cuenta."));
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}
