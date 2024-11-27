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
                        usuario.getHabilitado()))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario getUsuarioById(Long id) throws ResourceNotFoundException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario not found with id " + id));
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        // Verificar si el correo ya está registrado
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado: " + usuario.getCorreo());
        }

        // Verificar si el numeroIdentificacion ya está registrado
        if (usuarioRepository.existsByNumeroIdentificacion(usuario.getNumeroIdentificacion())) {
            throw new IllegalArgumentException(
                    "El número de identificación ya está registrado: " + usuario.getNumeroIdentificacion());
        }

        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuarioDetails) throws ResourceNotFoundException {
        Usuario usuario = getUsuarioById(id);

        // Verificar si el correo está siendo cambiado a uno que ya existe en otro
        // usuario
        if (!usuario.getCorreo().equals(usuarioDetails.getCorreo())) {
            if (usuarioRepository.existsByCorreo(usuarioDetails.getCorreo())) {
                throw new IllegalArgumentException(
                        "El correo electrónico ya está registrado: " + usuarioDetails.getCorreo());
            }
            usuario.setCorreo(usuarioDetails.getCorreo());
        }

        // Verificar si el numeroIdentificacion está siendo cambiado a uno que ya existe
        // en otro usuario
        if (!usuario.getNumeroIdentificacion().equals(usuarioDetails.getNumeroIdentificacion())) {
            if (usuarioRepository.existsByNumeroIdentificacion(usuarioDetails.getNumeroIdentificacion())) {
                throw new IllegalArgumentException(
                        "El número de identificación ya está registrado: " + usuarioDetails.getNumeroIdentificacion());
            }
            usuario.setNumeroIdentificacion(usuarioDetails.getNumeroIdentificacion());
        }

        // Verificar si la contraseña ha cambiado antes de encriptarla
        if (!passwordEncoder.matches(usuarioDetails.getPassword(), usuario.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
        }

        // Actualizar los demás campos
        usuario.setIdentificacion(usuarioDetails.getIdentificacion());
        usuario.setNombres(usuarioDetails.getNombres());
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
        usuario.setUpdatedBy(usuarioDetails.getUpdatedBy());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Long id) throws ResourceNotFoundException {
        Usuario usuario = getUsuarioById(id);
        usuarioRepository.delete(usuario);
    }
}
