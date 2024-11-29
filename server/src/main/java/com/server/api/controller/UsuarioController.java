package com.server.api.controller;

import com.server.api.model.Usuario;
import com.server.api.service.UsuarioService;
import com.server.api.dto.UsuarioConRolesDTO;
import com.server.api.dto.UsuarioDTO;
import com.server.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/usuarios")
@SecurityScheme(name = "Bearer Auth", description = "Autenticación JWT requerida para acceder a la mayoría de los endpoints", scheme = "Bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
@SecurityRequirement(name = "Bearer Auth")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener un usuario por ID
    @Operation(summary = "Obtener un usuario por ID", description = "Devuelve los detalles de un usuario específico mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }
        return ResponseEntity.ok(usuario);
    }

    // Crear un nuevo usuario
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos de usuario no válidos"),
            @ApiResponse(responseCode = "409", description = "Conflicto: El correo ya está registrado")
    })
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.saveUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    // Actualizar un usuario existente
    @Operation(summary = "Actualizar un usuario existente", description = "Actualiza los detalles de un usuario específico mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDetails) {
        Usuario updatedUsuario = usuarioService.updateUsuario(id, usuarioDetails);
        if (updatedUsuario == null) {
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }
        return ResponseEntity.ok(updatedUsuario);
    }

    // Eliminar un usuario
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario específico mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Usuario no encontrado con el ID: " + id);
        }
    }

    @Operation(summary = "Asignar roles a un usuario", description = "Asigna uno o varios roles a un usuario específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Roles asignados correctamente al usuario"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}/roles")
    public ResponseEntity<UsuarioDTO> assignRolesToUsuario(@PathVariable Long id, @RequestBody List<Long> roleIds) {
        UsuarioDTO updatedUsuario = usuarioService.assignRoles(id, roleIds);
        return ResponseEntity.ok(updatedUsuario);
    }

    @Operation(summary = "Obtener todos los usuarios con sus roles", description = "Devuelve una lista de todos los usuarios con sus roles asignados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuarios con sus roles obtenida correctamente")
    })
    @GetMapping("/roles")
    public ResponseEntity<List<UsuarioConRolesDTO>> getAllUsuariosWithRoles() {
        List<UsuarioConRolesDTO> usuariosConRoles = usuarioService.getAllUsuariosWithRoles();
        return ResponseEntity.ok(usuariosConRoles);
    }
}
