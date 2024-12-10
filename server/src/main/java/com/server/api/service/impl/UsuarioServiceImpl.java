package com.server.api.service.impl;

import com.server.api.model.Rol;
import com.server.api.model.Usuario;
import com.server.api.model.UsuarioRol;
import com.server.api.repository.RolRepository;
import com.server.api.repository.UsuarioRepository;
import com.server.api.repository.UsuarioRolRepository;
import com.server.api.service.UsuarioService;
import com.server.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

import com.server.api.dto.RolDTO;
import com.server.api.dto.UsuarioConRolesDTO;
import com.server.api.dto.UsuarioDTO;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> new UsuarioDTO(
                        usuario.getId(),
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
        try {
            // Validar si el correo ya está registrado
            if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
                throw new IllegalArgumentException("El correo electrónico ya está registrado: " + usuario.getCorreo());
            }

            // Validar si el número de identificación ya está registrado
            if (usuarioRepository.existsByNumeroIdentificacion(usuario.getNumeroIdentificacion())) {
                throw new IllegalArgumentException(
                        "El número de identificación ya está registrado: " + usuario.getNumeroIdentificacion());
            }

            // Encriptar la contraseña antes de guardar
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

            // Guardar el usuario base
            Usuario savedUsuario = usuarioRepository.save(usuario);

            // Asignar el rol por defecto (ID: 8)
            Rol defaultRol = rolRepository.findById(8L)
                    .orElseThrow(() -> new IllegalArgumentException("El rol 'Cliente' no existe."));

            UsuarioRol usuarioRol = UsuarioRol.builder()
                    .usuario(savedUsuario)
                    .rol(defaultRol)
                    .build();

            // Guardar la relación usuario-rol
            usuarioRolRepository.save(usuarioRol);

            return savedUsuario;
        } catch (Exception ex) {
            throw new RuntimeException("Error al guardar el usuario: " + ex.getMessage(), ex);
        }
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

    @Override
    public UsuarioDTO assignRoles(Long usuarioId, List<Long> roleIds) throws ResourceNotFoundException {
        // Buscar el usuario por ID
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el ID: " + usuarioId));

        // Limpiar los roles actuales del usuario
        usuarioRolRepository.deleteAllByUsuario(usuario);

        // Asignar los nuevos roles
        List<UsuarioRol> nuevosRoles = roleIds.stream()
                .map(roleId -> {
                    Rol rol = rolRepository.findById(roleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el ID: " + roleId));
                    return UsuarioRol.builder().usuario(usuario).rol(rol).build();
                })
                .collect(Collectors.toList());

        // Guardar los nuevos roles asignados
        usuarioRolRepository.saveAll(nuevosRoles);

        // Crear y devolver el DTO
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombres(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getCiudadResidencia() != null ? usuario.getCiudadResidencia().getId() : null,
                usuario.getProfesion() != null ? usuario.getProfesion().getId() : null,
                usuario.getTipoTrabajo() != null ? usuario.getTipoTrabajo().getId() : null,
                usuario.getEstadoCivil() != null ? usuario.getEstadoCivil().getId() : null,
                usuario.getNivelEducativo() != null ? usuario.getNivelEducativo().getId() : null,
                usuario.getHabilitado());
    }

    @Override
    public List<UsuarioConRolesDTO> getAllUsuariosWithRoles() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Convertir cada Usuario a UsuarioConRolesDTO y mapear los roles desde
        // UsuarioRol
        return usuarios.stream().map(usuario -> {
            List<UsuarioRol> usuarioRoles = usuarioRolRepository.findByUsuario(usuario);

            List<RolDTO> roles = usuarioRoles.stream()
                    .map(usuarioRol -> {
                        Rol rol = usuarioRol.getRol();
                        return new RolDTO(rol.getId(), rol.getNombre(), rol.getDescripcion(), rol.getHabilitado());
                    })
                    .collect(Collectors.toList());

            return new UsuarioConRolesDTO(
                    usuario.getId(),
                    usuario.getNombres(),
                    usuario.getCorreo(),
                    usuario.getCiudadResidencia() != null ? usuario.getCiudadResidencia().getId() : null,
                    usuario.getHabilitado(),
                    roles);
        }).collect(Collectors.toList());
    }
}
