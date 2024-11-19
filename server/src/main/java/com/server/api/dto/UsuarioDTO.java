package com.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String nombres;
    private String correo;
    private String telefono;
    private String direccion;
    private Long ciudadResidenciaId;
    private Long profesionId;
    private Long tipoTrabajoId;
    private Long estadoCivilId;
    private Long nivelEducativoId;
    private Boolean habilitado;
}
