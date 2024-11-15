package com.server.api.model;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles_permisos")
public class RolPermiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "id", nullable = false) // Foreign Key hacia la tabla roles
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "permiso_id", referencedColumnName = "id", nullable = false) // Foreign Key hacia la tabla permisos
    private Permiso permiso;

    @Column(nullable = false)
    private Boolean habilitado = true;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}