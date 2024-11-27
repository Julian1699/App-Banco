package com.server.api.controller;

import com.server.api.model.Rol;
import com.server.api.service.RolService;
import com.server.api.dto.RolDTO;
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
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/roles")
@SecurityScheme(name = "Bearer Auth",
        description = "Autenticación JWT requerida para acceder a la mayoría de los endpoints",
        scheme = "Bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Tag(name = "Roles", description = "Operaciones relacionadas con los roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Operation(summary = "Obtener todos los roles", description = "Devuelve una lista de todos los roles registrados, con filtros opcionales por nombre y estado habilitado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de roles obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<RolDTO>> getAllRoles(@RequestParam(required = false) String nombre,
                                                    @RequestParam(required = false) Boolean habilitado) {
        List<RolDTO> roles = rolService.getAllRoles(nombre, habilitado);
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Obtener un rol por ID", description = "Devuelve los detalles de un rol específico mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol encontrado"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Rol> getRolById(@PathVariable Long id) {
        Rol rol = rolService.getRolById(id);
        if (rol == null) {
            throw new ResourceNotFoundException("Rol no encontrado con el ID: " + id);
        }
        return ResponseEntity.ok(rol);
    }

    @Operation(summary = "Crear un nuevo rol", description = "Registra un nuevo rol en la base de datos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Rol creado con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta, datos de rol no válidos")
    })
    @PostMapping
    public ResponseEntity<RolDTO> createRol(@Valid @RequestBody RolDTO rolDTO) {
        RolDTO savedRol = rolService.saveRol(rolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRol);
    }

    @Operation(summary = "Actualizar un rol existente", description = "Actualiza los detalles de un rol específico mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> updateRol(@PathVariable Long id, @Valid @RequestBody RolDTO rolDTO) {
        RolDTO updatedRol = rolService.updateRol(id, rolDTO);
        return ResponseEntity.ok(updatedRol);
    }    

    @Operation(summary = "Eliminar un rol", description = "Elimina un rol específico mediante su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        try {
            rolService.deleteRol(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Rol no encontrado con el ID: " + id);
        }
    }
}