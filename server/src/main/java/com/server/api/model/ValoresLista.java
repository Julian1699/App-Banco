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
@Table(name = "valores_listas")
public class ValoresLista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lista_id")
    private Lista lista;

    @Column(nullable = false, length = 100)
    private String valor;

    @Column
    private String descripcion;

    @Column
    private Integer orden;

    @Column(nullable = false)
    private Boolean habilitado = true;

    @Column(length = 20)
    private String codigo;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;
}
