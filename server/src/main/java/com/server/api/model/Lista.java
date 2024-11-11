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
@Table(name = "listas")
public class Lista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private Boolean habilitado = true;

    @Column
    private Integer orden;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValoresLista> valores;
}

