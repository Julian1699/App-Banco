package com.server.api.service.impl;

import com.server.api.dto.RolDTO;
import com.server.api.exception.ResourceNotFoundException;
import com.server.api.model.Rol;
import com.server.api.model.Usuario;
import com.server.api.repository.RolRepository;
import com.server.api.repository.UsuarioRepository;
import com.server.api.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<RolDTO> getAllRoles(String nombre, Boolean habilitado) {
        List<Rol> roles = rolRepository.findAll();

        // Filtrar por nombre y habilitado si se proporcionan
        if (nombre != null) {
            roles = roles.stream().filter(rol -> rol.getNombre().equalsIgnoreCase(nombre)).collect(Collectors.toList());
        }
        if (habilitado != null) {
            roles = roles.stream().filter(rol -> rol.getHabilitado().equals(habilitado)).collect(Collectors.toList());
        }

        return roles.stream().map(rol -> new RolDTO(rol.getId(), rol.getNombre(), rol.getDescripcion(), rol.getHabilitado())).collect(Collectors.toList());
    }

    @Override
    public Rol getRolById(Long id) throws ResourceNotFoundException {
        return rolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el ID: " + id));
    }

    @Override
    public Rol saveRol(Rol rol) {
        // Obtener el usuario autenticado del contexto de seguridad
        Long usuarioIdAutenticado = getAuthenticatedUserId();

        // Asignar createdBy y createdAt
        if (rol.getCreatedAt() == null) {
            rol.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        rol.setCreatedBy(usuarioIdAutenticado != null ? usuarioIdAutenticado.toString() : null);

        return rolRepository.save(rol);
    }

    @Override
    public Rol updateRol(Long id, Rol rolDetails) throws ResourceNotFoundException {
        // Obtener el rol existente
        Rol rol = getRolById(id);

        // Actualizar los campos permitidos
        rol.setNombre(rolDetails.getNombre());
        rol.setDescripcion(rolDetails.getDescripcion());
        rol.setHabilitado(rolDetails.getHabilitado());

        // Asignar updatedBy y updatedAt
        rol.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        rol.setUpdatedBy(getAuthenticatedUserId() != null ? getAuthenticatedUserId().toString() : null);

        return rolRepository.save(rol);
    }

    @Override
    public void deleteRol(Long id) throws ResourceNotFoundException {
        Rol rol = getRolById(id);
        rolRepository.delete(rol);
    }

    // Método para obtener el ID del usuario autenticado del contexto de seguridad
    private Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            String username = ((User) principal).getUsername();
            // Aquí asumimos que el nombre de usuario es el correo y podemos obtener el usuario de la base de datos
            Usuario usuario = usuarioRepository.findByCorreo(username).orElse(null);
            return usuario != null ? usuario.getId() : null;
        } else {
            return null;
        }
    }
}