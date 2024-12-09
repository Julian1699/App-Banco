package com.server.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    List<Departamento> findByPaisIdAndHabilitadoTrue(Long paisId);
}