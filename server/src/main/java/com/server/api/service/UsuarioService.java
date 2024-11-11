package com.server.api.service;

import com.server.api.model.Usuario;
import com.server.api.exception.ResourceNotFoundException;
import java.util.List;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Long id) throws ResourceNotFoundException;
    Usuario saveUsuario(Usuario usuario);
    Usuario updateUsuario(Long id, Usuario usuarioDetails) throws ResourceNotFoundException;
    void deleteUsuario(Long id) throws ResourceNotFoundException;
}
