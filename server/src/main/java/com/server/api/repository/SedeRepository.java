package com.server.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Long> {
   List<Sede> findByCiudadIdAndHabilitadoTrue(Long ciudadId);
}