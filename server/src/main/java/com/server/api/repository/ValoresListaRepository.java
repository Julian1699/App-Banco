package com.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.ValoresLista;

@Repository
public interface ValoresListaRepository extends JpaRepository<ValoresLista, Long> {
}