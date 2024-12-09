package com.server.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
   List<Pais> findAllByHabilitadoTrue();
}
