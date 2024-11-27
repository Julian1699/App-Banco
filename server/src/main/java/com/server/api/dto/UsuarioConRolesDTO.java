package com.server.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioConRolesDTO {
    private Long id;
    private String nombres;
    private String correo;
    private Long ciudadResidenciaId;
    private Boolean habilitado;
    private List<RolDTO> roles; // Lista de roles asignados al usuario
}
