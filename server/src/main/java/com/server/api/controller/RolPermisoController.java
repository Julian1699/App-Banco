package com.server.api.controller;

import com.server.api.dto.CategoriaDTO;
import com.server.api.dto.RolConPermisosDTO;
import com.server.api.service.RolPermisoService;
import com.server.api.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles y Permisos", description = "Operaciones relacionadas con los roles y permisos")
public class RolPermisoController {

    @Autowired
    private RolPermisoService rolPermisoService;

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
}
