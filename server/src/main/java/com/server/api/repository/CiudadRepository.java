package com.server.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {
   List<Ciudad> findByDepartamentoIdAndHabilitadoTrue(Long departamentoId);
}
