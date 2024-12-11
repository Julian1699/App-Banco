package com.server.api.service;

import com.server.api.model.Usuario;
import com.server.api.dto.RolDTO;
import com.server.api.dto.UsuarioConRolesDTO;
import com.server.api.dto.UsuarioCreacionDTO;
import com.server.api.dto.UsuarioDTO;
import com.server.api.exception.ResourceNotFoundException;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface UsuarioService {
    List<UsuarioDTO> getAllUsuarios();
    Usuario getUsuarioById(Long id) throws ResourceNotFoundException;
    ResponseEntity<String> saveUsuario(UsuarioCreacionDTO usuarioDTO);
    Usuario updateUsuario(Long id, Usuario usuarioDetails) throws ResourceNotFoundException;
    void deleteUsuario(Long id) throws ResourceNotFoundException;
    UsuarioDTO assignRoles(Long usuarioId, List<Long> roleIds) throws ResourceNotFoundException;
    List<UsuarioConRolesDTO> getAllUsuariosWithRoles();
}
