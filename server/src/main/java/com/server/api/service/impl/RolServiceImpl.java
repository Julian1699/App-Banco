package com.server.api.service.impl;

import com.server.api.dto.RolDTO;
import com.server.api.exception.ResourceNotFoundException;
import com.server.api.model.Rol;
import com.server.api.repository.RolRepository;
import com.server.api.service.RolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Timestamp;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

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
        if (rol.getCreatedAt() == null) {
            rol.setCreatedAt(new Timestamp(System.currentTimeMillis())); // Asigna la fecha actual
        }
        return rolRepository.save(rol);
    }
    
    @Override
    public Rol updateRol(Long id, Rol rolDetails) throws ResourceNotFoundException {
        Rol rol = getRolById(id);
        rol.setNombre(rolDetails.getNombre());
        rol.setDescripcion(rolDetails.getDescripcion());
        rol.setHabilitado(rolDetails.getHabilitado());
        rol.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Actualiza la fecha de modificación
        return rolRepository.save(rol);
    }

    @Override
    public void deleteRol(Long id) throws ResourceNotFoundException {
        Rol rol = getRolById(id);
        rolRepository.delete(rol);
    }
}