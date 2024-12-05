package com.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean habilitado;
}
