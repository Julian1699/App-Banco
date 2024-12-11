package com.server.api.service.impl;

import com.server.api.model.Rol;
import com.server.api.model.Sede;
import com.server.api.model.Usuario;
import com.server.api.model.UsuarioRol;
import com.server.api.model.UsuarioSede;
import com.server.api.model.ValoresLista;
import com.server.api.repository.CiudadRepository;
import com.server.api.repository.RolRepository;
import com.server.api.repository.SedeRepository;
import com.server.api.repository.UsuarioRepository;
import com.server.api.repository.UsuarioRolRepository;
import com.server.api.repository.UsuarioSedeRepository;
import com.server.api.repository.ValoresListaRepository;
import com.server.api.service.UsuarioService;

import jakarta.transaction.Transactional;

import com.server.api.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

import com.server.api.dto.RolDTO;
import com.server.api.dto.UsuarioConRolesDTO;
import com.server.api.dto.UsuarioCreacionDTO;
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
        private SedeRepository sedeRepository;

        @Autowired
        private ValoresListaRepository valoresListaRepository;

        @Autowired
        private UsuarioSedeRepository usuarioSedeRepository;

        @Autowired
        private CiudadRepository ciudadRepository;

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
        @Transactional
        public ResponseEntity<String> saveUsuario(UsuarioCreacionDTO usuarioDTO) {
                try {
                        // Validar si el correo ya está registrado
                        if (usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
                                throw new IllegalArgumentException(
                                                "El correo ya está registrado: " + usuarioDTO.getCorreo());
                        }

                        // Validar si el número de identificación ya está registrado
                        if (usuarioRepository.existsByNumeroIdentificacion(usuarioDTO.getNumeroIdentificacion())) {
                                throw new IllegalArgumentException(
                                                "El número de identificación ya está registrado: "
                                                                + usuarioDTO.getNumeroIdentificacion());
                        }

                        // Obtener el tipo de identificación
                        ValoresLista identificacion = valoresListaRepository.findById(usuarioDTO.getIdentificacionId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Tipo de identificación no encontrado con ID: "
                                                                        + usuarioDTO.getIdentificacionId()));

                        // Obtener el género
                        ValoresLista genero = valoresListaRepository.findById(usuarioDTO.getGeneroId())
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                        "Género no encontrado con ID: " + usuarioDTO.getGeneroId()));

                        // Crear el usuario
                        Usuario usuario = Usuario.builder()
                                        .nombres(usuarioDTO.getNombres())
                                        .correo(usuarioDTO.getCorreo())
                                        .numeroIdentificacion(usuarioDTO.getNumeroIdentificacion())
                                        .password(passwordEncoder.encode(usuarioDTO.getPassword()))
                                        .telefono(usuarioDTO.getTelefono())
                                        .direccion(usuarioDTO.getDireccion())
                                        .habilitado(usuarioDTO.getHabilitado())
                                        .ciudadResidencia(ciudadRepository.findById(usuarioDTO.getCiudadResidenciaId())
                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                        "Ciudad no encontrada con ID: " + usuarioDTO
                                                                                        .getCiudadResidenciaId())))
                                        .profesion(usuarioDTO.getProfesionId() != null
                                                        ? valoresListaRepository.findById(usuarioDTO.getProfesionId())
                                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                                        "Profesión no encontrada con ID: "
                                                                                                        + usuarioDTO.getProfesionId()))
                                                        : null)
                                        .tipoTrabajo(usuarioDTO.getTipoTrabajoId() != null
                                                        ? valoresListaRepository.findById(usuarioDTO.getTipoTrabajoId())
                                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                                        "Tipo de trabajo no encontrado con ID: "
                                                                                                        + usuarioDTO.getTipoTrabajoId()))
                                                        : null)
                                        .estadoCivil(usuarioDTO.getEstadoCivilId() != null
                                                        ? valoresListaRepository.findById(usuarioDTO.getEstadoCivilId())
                                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                                        "Estado civil no encontrado con ID: "
                                                                                                        + usuarioDTO.getEstadoCivilId()))
                                                        : null)
                                        .nivelEducativo(usuarioDTO.getNivelEducativoId() != null
                                                        ? valoresListaRepository
                                                                        .findById(usuarioDTO.getNivelEducativoId())
                                                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                                                        "Nivel educativo no encontrado con ID: "
                                                                                                        + usuarioDTO.getNivelEducativoId()))
                                                        : null)
                                        .identificacion(identificacion)
                                        .genero(genero)
                                        .ingresos(usuarioDTO.getIngresos())
                                        .egresos(usuarioDTO.getEgresos())
                                        .build();

                        // Guardar usuario
                        Usuario savedUsuario = usuarioRepository.save(usuario);

                        // Asignar el rol por defecto (ID: 8)
                        Rol defaultRol = rolRepository.findById(8L)
                                        .orElseThrow(() -> new IllegalArgumentException("El rol 'Cliente' no existe."));

                        UsuarioRol usuarioRol = UsuarioRol.builder()
                                        .usuario(savedUsuario)
                                        .rol(defaultRol)
                                        .build();

                        // Guardar la relación en usuario_rol
                        usuarioRolRepository.save(usuarioRol);

                        // Asignar sede si se proporciona en el JSON
                        if (usuarioDTO.getSedeId() != null) {
                                Sede sede = sedeRepository.findById(usuarioDTO.getSedeId())
                                                .orElseThrow(() -> new ResourceNotFoundException(
                                                                "Sede no encontrada con ID: "
                                                                                + usuarioDTO.getSedeId()));

                                UsuarioSede usuarioSede = UsuarioSede.builder()
                                                .usuario(savedUsuario)
                                                .sede(sede)
                                                .build();

                                // Guardar la relación en usuario_sede
                                usuarioSedeRepository.save(usuarioSede);
                        } else {
                                throw new IllegalArgumentException("El campo sede es obligatorio.");
                        }

                        // Devuelve un mensaje simple
                        return ResponseEntity.status(HttpStatus.CREATED).body("¡Usuario creado con éxito!");

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
