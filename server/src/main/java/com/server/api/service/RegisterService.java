package com.server.api.service;

import com.server.api.dto.CiudadDTO;
import com.server.api.dto.DepartamentoDTO;
import com.server.api.dto.PaisDTO;
import com.server.api.dto.SedeDTO;

import java.util.List;

public interface RegisterService {
    List<PaisDTO> getAllPaises();
    List<DepartamentoDTO> getDepartamentosByPais(Long paisId);
    List<CiudadDTO> getCiudadesByDepartamento(Long departamentoId);
    List<SedeDTO> getSedesByCiudad(Long ciudadId);
}
