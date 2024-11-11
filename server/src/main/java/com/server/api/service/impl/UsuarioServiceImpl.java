package com.server.api.service.impl;

import com.server.api.model.Usuario;
import com.server.api.repository.UsuarioRepository;
import com.server.api.service.UsuarioService;
import com.server.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.sql.Timestamp;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Long id) throws ResourceNotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) throws ResourceNotFoundException {
        Usuario usuario = getUsuarioById(id);

        usuario.setIdentificacion(usuarioDetails.getIdentificacion());
        usuario.setNumeroIdentificacion(usuarioDetails.getNumeroIdentificacion());
        usuario.setNombres(usuarioDetails.getNombres());
        usuario.setCorreo(usuarioDetails.getCorreo());
        usuario.setPassword(usuarioDetails.getPassword());
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
