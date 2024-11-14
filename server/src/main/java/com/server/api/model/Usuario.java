package com.server.api.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = {"identificacion_id", "numero_identificacion"}))
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "identificacion_id", nullable = false)
    private ValoresLista identificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 50)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombres;

    @NotBlank(message = "El campo correo no puede estar vacío")
    @Column(unique = true, length = 100) // Asegura que no haya correos duplicados
    private String correo;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 255)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "ciudad_residencia_id", nullable = false)
    private Ciudad ciudadResidencia;

    @ManyToOne
    @JoinColumn(name = "profesion_id")
    private ValoresLista profesion;

    @ManyToOne
    @JoinColumn(name = "tipo_trabajo_id")
    private ValoresLista tipoTrabajo;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id")
    private ValoresLista estadoCivil;

    @ManyToOne
    @JoinColumn(name = "nivel_educativo_id")
    private ValoresLista nivelEducativo;

    @Column(precision = 15, scale = 2)
    private BigDecimal ingresos;

    @Column(precision = 15, scale = 2)
    private BigDecimal egresos;

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

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaBancaria> cuentasBancarias;

    @ManyToMany
    @JoinTable(name = "usuario_sede", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "sede_id"))
    private List<Sede> sedes;
}
