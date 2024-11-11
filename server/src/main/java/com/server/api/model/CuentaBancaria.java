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
@Table(name = "cuenta_bancaria")
public class CuentaBancaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "tipo_cuenta_id", nullable = false)
    private ValoresLista tipoCuenta;

    @Column(name = "codigo_seguridad", nullable = false, length = 200)
    private String codigoSeguridad;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean habilitado = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;
}

