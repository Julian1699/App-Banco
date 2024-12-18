package com.server.api.service;

import com.server.api.dto.RolDTO;
import com.server.api.model.Rol;
import com.server.api.exception.ResourceNotFoundException;

import java.util.List;

public interface RolService {
    List<RolDTO> getAllRoles(String nombre, Boolean habilitado);
    Rol getRolById(Long id) throws ResourceNotFoundException;
    RolDTO saveRol(RolDTO rolDTO);
    RolDTO updateRol(Long id, RolDTO rolDTO) throws ResourceNotFoundException;
    void deleteRol(Long id) throws ResourceNotFoundException;
}
