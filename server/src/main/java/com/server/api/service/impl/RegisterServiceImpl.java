package com.server.api.service.impl;

import com.server.api.dto.CiudadDTO;
import com.server.api.dto.DepartamentoDTO;
import com.server.api.dto.PaisDTO;
import com.server.api.dto.SedeDTO;
import com.server.api.model.Ciudad;
import com.server.api.model.Departamento;
import com.server.api.model.Pais;
import com.server.api.model.Sede;
import com.server.api.repository.CiudadRepository;
import com.server.api.repository.DepartamentoRepository;
import com.server.api.repository.PaisRepository;
import com.server.api.repository.SedeRepository;
import com.server.api.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public List<PaisDTO> getAllPaises() {
        List<Pais> paises = paisRepository.findAllByHabilitadoTrue();
        return paises.stream().map(pais -> new PaisDTO(pais.getId(), pais.getNombre())).collect(Collectors.toList());
    }

    @Override
    public List<DepartamentoDTO> getDepartamentosByPais(Long paisId) {
        List<Departamento> departamentos = departamentoRepository.findByPaisIdAndHabilitadoTrue(paisId);
        return departamentos.stream().map(depto -> new DepartamentoDTO(depto.getId(), depto.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CiudadDTO> getCiudadesByDepartamento(Long departamentoId) {
        List<Ciudad> ciudades = ciudadRepository.findByDepartamentoIdAndHabilitadoTrue(departamentoId);
        return ciudades.stream().map(ciudad -> new CiudadDTO(ciudad.getId(), ciudad.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SedeDTO> getSedesByCiudad(Long ciudadId) {
        List<Sede> sedes = sedeRepository.findByCiudadIdAndHabilitadoTrue(ciudadId);
        return sedes.stream().map(sede -> new SedeDTO(sede.getId(), sede.getNombre(), sede.getDireccion()))
                .collect(Collectors.toList());
    }
}
