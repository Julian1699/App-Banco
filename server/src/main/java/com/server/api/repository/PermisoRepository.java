package com.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}