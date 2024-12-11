package com.server.api.dto;

import java.math.BigDecimal;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioCreacionDTO {
    private String nombres;
    private String correo;
    private String numeroIdentificacion;
    private String password;
    private String telefono;
    private String direccion;
    private Long ciudadResidenciaId;
    private Long profesionId;
    private Long tipoTrabajoId;
    private Long estadoCivilId;
    private Long nivelEducativoId;
    private Long identificacionId;
    private Long generoId;
    private BigDecimal ingresos;
    private BigDecimal egresos;
    private Boolean habilitado;
    private Long sedeId;
}

