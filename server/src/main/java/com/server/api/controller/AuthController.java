package com.server.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.server.api.model.RolPermiso;
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

    // Mapa en memoria para rastrear los intentos fallidos de inicio de sesión
    private final Map<String, Integer> loginAttempts = new HashMap<>();

    // Máximo número de intentos fallidos permitidos
    private static final int MAX_ATTEMPTS = 3;

    @Operation(summary = "Iniciar sesión en el sistema", description = "Con las credenciales correctas, devuelve un token Bearer para usar en cada solicitud HTTP.", tags = {
            "Login" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario autenticado"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, campos no válidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginDTO loginDTO) {
        String correo = loginDTO.getCorreo();

        // Verificar si el usuario está registrado en la base de datos
        Optional<Usuario> usuarioOptional = userRepository.findByCorreo(correo);

        if (usuarioOptional.isEmpty()) {
            // Si el usuario no existe, devolver un mensaje de error
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "El correo electrónico no está registrado."));
        }

        Usuario usuario = usuarioOptional.get();

        // Verificar si el usuario está habilitado (bloqueado)
        if (!usuario.getHabilitado()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error",
                            "Usuario bloqueado, por favor contacte al soporte para desbloquear la cuenta."));
        }

        // Crear un token de autenticación para validar credenciales
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(correo,
                loginDTO.getPassword());
        try {
            // Intentar autenticar al usuario con el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(login);

            // Si la autenticación es exitosa
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                // Restablecer el contador de intentos fallidos si la autenticación es correcta
                loginAttempts.remove(correo);

                // Obtener permisos del usuario a partir de sus roles
                List<String> permisos = usuario.getRoles().stream()
                        .flatMap(rol -> rol.getRolPermisos().stream())
                        .filter(RolPermiso::getHabilitado) // Solo agregar los permisos que estén habilitados
                        .map(rolPermiso -> rolPermiso.getPermiso().getNombre())
                        .distinct() // Para evitar permisos duplicados en caso de múltiples roles
                        .collect(Collectors.toList());

                                        // Obtener roles del usuario
                List<String> roles = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .collect(Collectors.toList());

                // Crear el token JWT con la información del usuario, incluyendo permisos
                String jwt = jwtUtil.create(
                        usuario.getId(), // ID del usuario
                        usuario.getCorreo(), // Correo electrónico del usuario
                        usuario.getNombres(), // Nombre del usuario
                        usuario.getHabilitado(), // Estado de habilitación
                        roles, // Lista de roles del usuario
                        permisos // Lista de permisos del usuario
                );

                // Construir la respuesta con el token generado
                Map<String, String> response = new HashMap<>();
                response.put("token", jwt);
                return ResponseEntity.ok(response);
            } else {
                // Si la autenticación falla por alguna razón no especificada
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (BadCredentialsException e) {
            // Capturar excepción de credenciales incorrectas
            // Incrementar el contador de intentos fallidos para el usuario
            int attempts = loginAttempts.getOrDefault(correo, 0);
            attempts++;
            loginAttempts.put(correo, attempts);

            // Si se superan los intentos permitidos, bloquear al usuario
            if (attempts >= MAX_ATTEMPTS) {
                usuario.setHabilitado(false); // Actualizar el estado de habilitación en la base de datos
                userRepository.save(usuario); // Guardar el cambio en la base de datos

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error",
                                "Usuario bloqueado, por favor contacte al soporte para desbloquear la cuenta."));
            }

            // Responder con un mensaje de credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}
