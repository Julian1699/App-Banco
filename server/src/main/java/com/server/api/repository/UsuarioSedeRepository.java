package com.server.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.server.api.model.UsuarioSede;

@Repository
public interface UsuarioSedeRepository extends JpaRepository<UsuarioSede, Long> {
}
