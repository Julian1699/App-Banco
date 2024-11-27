package com.server.api.model;

import lombok.*;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permisos")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "modulo_id", foreignKey = @ForeignKey(name = "fk_permiso_modulo"), nullable = false)
    private ValoresLista modulo;  // Relación con ValoresLista para representar el módulo al que pertenece el permiso

    @Column(nullable = false)
    private Boolean habilitado = true;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @OneToMany(mappedBy = "permiso", cascade = CascadeType.ALL)
    private List<RolPermiso> rolPermisos;
}
