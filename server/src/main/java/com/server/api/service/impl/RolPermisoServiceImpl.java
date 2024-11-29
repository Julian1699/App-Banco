package com.server.api.service.impl;

import com.server.api.dto.CategoriaDTO;
import com.server.api.dto.ModuloDTO;
import com.server.api.dto.PermisoDTO;
import com.server.api.dto.RolConPermisosDTO;
import com.server.api.exception.ResourceNotFoundException;
import com.server.api.model.Lista;
import com.server.api.model.Permiso;
import com.server.api.model.Rol;
import com.server.api.model.RolPermiso;
import com.server.api.model.ValoresLista;
import com.server.api.repository.ListaRepository;
import com.server.api.repository.PermisoRepository;
import com.server.api.repository.RolPermisoRepository;
import com.server.api.repository.RolRepository;
import com.server.api.repository.ValoresListaRepository;
import com.server.api.service.RolPermisoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolPermisoServiceImpl implements RolPermisoService {

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private ValoresListaRepository valoresListaRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RolPermisoRepository rolPermisoRepository;

    @Override
    public CategoriaDTO getCategoriaConModulosYPermisos(String nombreCategoria) {
        Lista categoria = listaRepository.findAll().stream()
                .filter(lista -> lista.getNombre().equalsIgnoreCase(nombreCategoria))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada: " + nombreCategoria));

        List<ValoresLista> modulos = valoresListaRepository.findByLista(categoria);

        List<ModuloDTO> moduloDTOs = modulos.stream().map(modulo -> {
            List<Permiso> permisos = permisoRepository.findByModulo(modulo);
            List<PermisoDTO> permisoDTOs = permisos.stream()
                    .map(permiso -> new PermisoDTO(permiso.getId(), permiso.getNombre(), permiso.getDescripcion()))
                    .collect(Collectors.toList());

            return new ModuloDTO(modulo.getValor(), modulo.getDescripcion(), permisoDTOs);
        }).collect(Collectors.toList());

        return new CategoriaDTO(categoria.getNombre(), moduloDTOs);
    }

    @Override
    public void asignarPermisosARol(Long rolId, List<Long> permisosIds) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el ID: " + rolId));

        rolPermisoRepository.deleteByRol(rol);

        permisosIds.forEach(permisoId -> {
            Permiso permiso = permisoRepository.findById(permisoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Permiso no encontrado con el ID: " + permisoId));
            RolPermiso rolPermiso = new RolPermiso();
            rolPermiso.setRol(rol);
            rolPermiso.setPermiso(permiso);
            rolPermiso.setHabilitado(true);
            rolPermisoRepository.save(rolPermiso);
        });
    }

    @Override
    public RolConPermisosDTO getRolConPermisos(Long rolId) {
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con ID: " + rolId));

        List<PermisoDTO> permisosDTO = rol.getRolPermisos().stream()
                .map(rolPermiso -> {
                    Permiso permiso = rolPermiso.getPermiso();
                    return new PermisoDTO(permiso.getId(), permiso.getNombre(), permiso.getDescripcion());
                })
                .collect(Collectors.toList());

        return new RolConPermisosDTO(rol.getId(), rol.getNombre(), permisosDTO);
    }

    @Override
    public List<CategoriaDTO> getTodasLasCategoriasConModulosYPermisos() {
        // Filtrar solo las listas con descripción "Categoría"
        List<Lista> categorias = listaRepository.findAll().stream()
                .filter(lista -> "Categoría".equalsIgnoreCase(lista.getDescripcion()))
                .collect(Collectors.toList());

        // Mapear las categorías a DTOs con sus módulos y permisos
        return categorias.stream().map(categoria -> {
            // Buscar los módulos asociados a la categoría
            List<ValoresLista> modulos = valoresListaRepository.findByLista(categoria);

            // Mapear cada módulo a su DTO, incluyendo sus permisos
            List<ModuloDTO> moduloDTOs = modulos.stream().map(modulo -> {
                List<Permiso> permisos = permisoRepository.findByModulo(modulo);
                List<PermisoDTO> permisoDTOs = permisos.stream()
                        .map(permiso -> new PermisoDTO(permiso.getId(), permiso.getNombre(), permiso.getDescripcion()))
                        .collect(Collectors.toList());

                return new ModuloDTO(modulo.getValor(), modulo.getDescripcion(), permisoDTOs);
            }).collect(Collectors.toList());

            // Mapear la categoría y sus módulos
            return new CategoriaDTO(categoria.getNombre(), moduloDTOs);
        }).collect(Collectors.toList());
    }

    public List<RolPermiso> getPermisosPorRol(Long rolId) {
        // Recuperar solo los permisos que están habilitados
        return rolPermisoRepository.findByRolIdAndHabilitadoTrue(rolId);
    }

    public List<Long> getPermisosIdsPorRol(Long rolId) {
        // Filtrar y obtener solo los permisos habilitados
        return rolPermisoRepository.findByRolIdAndHabilitadoTrue(rolId)
                .stream()
                .map(rolPermiso -> rolPermiso.getPermiso().getId())
                .collect(Collectors.toList());
    }
}
