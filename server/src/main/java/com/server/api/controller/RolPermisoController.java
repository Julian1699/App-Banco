package com.server.api.controller;

import com.server.api.dto.CategoriaDTO;
import com.server.api.dto.RolConPermisosDTO;
import com.server.api.dto.UsuarioConRolesDTO;
import com.server.api.dto.UsuarioDTO;
import com.server.api.service.RolPermisoService;
import com.server.api.service.UsuarioService;
import com.server.api.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles_permisos")
@SecurityRequirement(name = "Bearer Auth")
@Tag(name = "Roles y Permisos", description = "Operaciones relacionadas con los roles y permisos")
public class RolPermisoController {

    @Autowired
    private RolPermisoService rolPermisoService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todas las categorías con sus módulos y permisos", description = "Devuelve todas las categorías junto con sus módulos y permisos asociados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categorías obtenidas correctamente")
    })
    @GetMapping("/categorias/modulos-permisos")
    public ResponseEntity<List<CategoriaDTO>> getTodasLasCategoriasConModulosYPermisos() {
        List<CategoriaDTO> categoriasDTO = rolPermisoService.getTodasLasCategoriasConModulosYPermisos();
        return ResponseEntity.ok(categoriasDTO);
    }

    @Operation(summary = "Asignar permisos a un rol", description = "Asigna uno o varios permisos a un rol específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permisos asignados correctamente al rol"),
            @ApiResponse(responseCode = "404", description = "Rol o Permiso no encontrado")
    })
    @PutMapping("/{rolId}/permisos")
    public ResponseEntity<Void> asignarPermisosARol(@PathVariable Long rolId, @RequestBody List<Long> permisosIds) {
        rolPermisoService.asignarPermisosARol(rolId, permisosIds);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtener un rol con sus permisos", description = "Devuelve un rol específico junto con sus permisos asociados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Rol obtenido con éxito"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
    })
    @GetMapping("/{rolId}/permisos")
    public ResponseEntity<RolConPermisosDTO> getRolConPermisos(@PathVariable Long rolId) {
        RolConPermisosDTO rolConPermisosDTO = rolPermisoService.getRolConPermisos(rolId);
        return ResponseEntity.ok(rolConPermisosDTO);
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
