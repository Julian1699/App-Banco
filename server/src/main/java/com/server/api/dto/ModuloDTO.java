package com.server.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuloDTO {
    private String nombre;
    private String descripcion;
    private List<PermisoDTO> permisos;
}

