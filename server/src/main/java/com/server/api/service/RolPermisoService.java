package com.server.api.service;

import java.util.List;

import com.server.api.dto.CategoriaDTO;
import com.server.api.dto.RolConPermisosDTO;

public interface RolPermisoService {
    CategoriaDTO getCategoriaConModulosYPermisos(String nombreCategoria);
    void asignarPermisosARol(Long rolId, List<Long> permisosIds);
    RolConPermisosDTO getRolConPermisos(Long rolId);
    List<CategoriaDTO> getTodasLasCategoriasConModulosYPermisos();
}
