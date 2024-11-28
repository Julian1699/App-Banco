package com.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolConPermisosDTO {
    private Long id;
    private String nombre;
    private List<PermisoDTO> permisos; // Lista de permisos asociados al rol
}
