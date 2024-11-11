package com.server.api.model;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "departamentos", uniqueConstraints = @UniqueConstraint(columnNames = {"nombre", "pais_id"}))
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    private Pais pais;

    @Column(length = 10)
    private String codigo;

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

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ciudad> ciudades;
}

