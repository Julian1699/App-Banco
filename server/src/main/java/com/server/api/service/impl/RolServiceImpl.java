package com.server.api.service.impl;

import com.server.api.dto.RolDTO;
import com.server.api.exception.ResourceNotFoundException;
import com.server.api.model.Rol;
import com.server.api.model.Usuario;
import com.server.api.repository.RolRepository;
import com.server.api.repository.UsuarioRepository;
import com.server.api.service.RolService;
import com.server.api.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

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

        return roles.stream()
                .map(rol -> new RolDTO(rol.getId(), rol.getNombre(), rol.getDescripcion(), rol.getHabilitado()))
                .collect(Collectors.toList());
    }

    @Override
    public Rol getRolById(Long id) throws ResourceNotFoundException {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el ID: " + id));
    }

    @Override
    public Rol saveRol(Rol rol) {
        // Establecer valores automáticos para crear un rol
        Long usuarioIdAutenticado = getAuthenticatedUserId();

        // Asignar campos automáticos
        rol.setHabilitado(true); // El campo habilitado siempre es true al crear un nuevo rol
        if (rol.getCreatedAt() == null) {
            rol.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        }
        rol.setCreatedBy(usuarioIdAutenticado != null ? usuarioIdAutenticado.toString() : null);

        return rolRepository.save(rol);
    }

    @Override
    public Rol updateRol(Long id, Rol rolDetails) throws ResourceNotFoundException {
        // Obtener el rol existente de la base de datos
        Rol rol = getRolById(id);

        // Actualizar los campos permitidos
        rol.setNombre(rolDetails.getNombre());
        rol.setDescripcion(rolDetails.getDescripcion());
        rol.setHabilitado(rolDetails.getHabilitado()); // Actualizar con el nuevo valor

        // Asignar campos automáticos
        rol.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        Long usuarioIdAutenticado = getAuthenticatedUserId();
        rol.setUpdatedBy(usuarioIdAutenticado != null ? usuarioIdAutenticado.toString() : null);

        return rolRepository.save(rol);
    }

    @Override
    public void deleteRol(Long id) throws ResourceNotFoundException {
        Rol rol = getRolById(id);
        rolRepository.delete(rol);
    }

    // Método para obtener el ID del usuario autenticado desde el token JWT
    private Long getAuthenticatedUserId() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7); // Eliminar "Bearer " del header para obtener el token
            String correo = jwtUtil.getUsername(jwt); // Obtener el correo del JWT
            if (correo != null) {
                Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
                return usuario != null ? usuario.getId() : null;
            }
        }
        return null;
    }
}
