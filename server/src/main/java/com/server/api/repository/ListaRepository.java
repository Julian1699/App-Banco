package com.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Lista;

@Repository
public interface ListaRepository extends JpaRepository<Lista, Long> {
}
