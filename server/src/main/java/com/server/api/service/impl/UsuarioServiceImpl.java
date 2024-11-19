package com.server.api.service.impl;

import com.server.api.model.Usuario;
import com.server.api.repository.UsuarioRepository;
import com.server.api.service.UsuarioService;
import com.server.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import com.server.api.dto.UsuarioDTO;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getNombres(),
                        usuario.getCorreo(),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getCiudadResidencia() != null ? usuario.getCiudadResidencia().getId() : null,
                        usuario.getProfesion() != null ? usuario.getProfesion().getId() : null,
                        usuario.getTipoTrabajo() != null ? usuario.getTipoTrabajo().getId() : null,
                        usuario.getEstadoCivil() != null ? usuario.getEstadoCivil().getId() : null,
                        usuario.getNivelEducativo() != null ? usuario.getNivelEducativo().getId() : null,
                        usuario.getHabilitado()
                ))
                .collect(Collectors.toList());
    }    
  
    @Override
    public Usuario getUsuarioById(Long id) throws ResourceNotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
       
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // Establecer el valor de `created_at`
        if (usuario.getCreatedAt() == null) {
            usuario.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) throws ResourceNotFoundException {
        Usuario usuario = getUsuarioById(id);

        usuario.setIdentificacion(usuarioDetails.getIdentificacion());
        usuario.setNumeroIdentificacion(usuarioDetails.getNumeroIdentificacion());
        usuario.setNombres(usuarioDetails.getNombres());
        usuario.setCorreo(usuarioDetails.getCorreo());

        // Verificar si la contraseña ha cambiado antes de encriptarla
        if (!usuario.getPassword().equals(usuarioDetails.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
        }

        usuario.setTelefono(usuarioDetails.getTelefono());
        usuario.setDireccion(usuarioDetails.getDireccion());
        usuario.setCiudadResidencia(usuarioDetails.getCiudadResidencia());
        usuario.setProfesion(usuarioDetails.getProfesion());
        usuario.setTipoTrabajo(usuarioDetails.getTipoTrabajo());
        usuario.setEstadoCivil(usuarioDetails.getEstadoCivil());
        usuario.setNivelEducativo(usuarioDetails.getNivelEducativo());
        usuario.setIngresos(usuarioDetails.getIngresos());
        usuario.setEgresos(usuarioDetails.getEgresos());
        usuario.setHabilitado(usuarioDetails.getHabilitado());
        usuario.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        usuario.setUpdatedBy(usuarioDetails.getUpdatedBy());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Long id) throws ResourceNotFoundException {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }
}
