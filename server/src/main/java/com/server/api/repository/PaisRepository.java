package com.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Pais;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long> {
}
