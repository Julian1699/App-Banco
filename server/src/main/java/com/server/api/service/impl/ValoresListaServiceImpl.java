package com.server.api.service.impl;

import com.server.api.dto.ValoresListaDTO;
import com.server.api.model.ValoresLista;
import com.server.api.repository.ValoresListaRepository;
import com.server.api.service.ValoresListaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValoresListaServiceImpl implements ValoresListaService {

    @Autowired
    private ValoresListaRepository valoresListaRepository;

    @Override
    public List<ValoresListaDTO> getValoresByListaId(Long listaId) {
        // Obtener la lista de entidades desde el repositorio
        List<ValoresLista> valores = valoresListaRepository.findByLista_IdAndHabilitadoTrue(listaId);

        // Mapear cada entidad a un DTO y devolverlo
        return valores.stream()
                .map(valor -> new ValoresListaDTO(
                        valor.getId(),
                        valor.getValor() // Solo mapeamos los campos necesarios
                ))
                .collect(Collectors.toList());
    }
}

