package com.server.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.api.model.Rol;
import com.server.api.model.RolPermiso;

@Repository
public interface RolPermisoRepository extends JpaRepository<RolPermiso, Long> {
    List<RolPermiso> findByRol(Rol rol);
    void deleteByRol(Rol rol);
    List<RolPermiso> findByRolIdAndHabilitadoTrue(Long rolId);
}
