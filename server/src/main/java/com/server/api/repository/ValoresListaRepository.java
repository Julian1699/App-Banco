package com.server.api.repository;

import com.server.api.model.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.ValoresLista;

import java.util.List;

@Repository
public interface ValoresListaRepository extends JpaRepository<ValoresLista, Long> {
    List<ValoresLista> findByLista(Lista lista);
    List<ValoresLista> findByLista_IdAndHabilitadoTrue(Long listaId);
}