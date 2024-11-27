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
    public RolDTO saveRol(RolDTO rolDTO) {
        // Obtener el usuario autenticado
        Usuario usuarioAutenticado = getAuthenticatedUser();
    
        if (usuarioAutenticado == null) {
            throw new IllegalArgumentException("No se pudo identificar al usuario autenticado para realizar la operación.");
        }
    
        // Convertir DTO a entidad Rol
        Rol rol = new Rol();
        rol.setNombre(rolDTO.getNombre());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setHabilitado(true);  // Siempre es true cuando se crea un rol
    
        // Asignar campos automáticos
        rol.setCreatedBy(usuarioAutenticado);
        rol.setCreatedAt(new Timestamp(System.currentTimeMillis()));
    
        // Guardar el rol
        Rol savedRol = rolRepository.save(rol);
    
        // Convertir la entidad guardada a DTO para la respuesta
        return new RolDTO(
                savedRol.getId(),
                savedRol.getNombre(),
                savedRol.getDescripcion(),
                savedRol.getHabilitado()
        );
    }    

    @Override
    public RolDTO updateRol(Long id, RolDTO rolDTO) throws ResourceNotFoundException {
        // Obtener el rol existente de la base de datos
        Rol rol = getRolById(id);
    
        // Obtener el usuario autenticado
        Usuario usuarioAutenticado = getAuthenticatedUser();
    
        if (usuarioAutenticado == null) {
            throw new IllegalArgumentException("No se pudo identificar al usuario autenticado para realizar la operación.");
        }
    
        // Actualizar los campos permitidos
        rol.setNombre(rolDTO.getNombre());
        rol.setDescripcion(rolDTO.getDescripcion());
        rol.setHabilitado(rolDTO.getHabilitado());
    
        // Asignar campos automáticos de actualización
        rol.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        rol.setUpdatedBy(usuarioAutenticado);
    
        // Guardar el rol actualizado
        Rol updatedRol = rolRepository.save(rol);
    
        // Convertir la entidad a DTO para la respuesta
        return new RolDTO(
                updatedRol.getId(),
                updatedRol.getNombre(),
                updatedRol.getDescripcion(),
                updatedRol.getHabilitado()
        );
    }    

    @Override
    public void deleteRol(Long id) throws ResourceNotFoundException {
        Rol rol = getRolById(id);
        rolRepository.delete(rol);
    }

    // Método para obtener el Usuario autenticado desde el token JWT
    private Usuario getAuthenticatedUser() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7); // Eliminar "Bearer " del header para obtener el token
            String correo = jwtUtil.getUsername(jwt); // Obtener el correo del JWT
            if (correo != null) {
                return usuarioRepository.findByCorreo(correo).orElse(null);
            }
        }
        return null;
    }
}
