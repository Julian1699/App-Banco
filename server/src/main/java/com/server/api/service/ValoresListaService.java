package com.server.api.service;

import java.util.List;

import com.server.api.dto.ValoresListaDTO;
import com.server.api.model.ValoresLista;

public interface ValoresListaService {
    List<ValoresListaDTO> getValoresByListaId(Long listaId);
}
