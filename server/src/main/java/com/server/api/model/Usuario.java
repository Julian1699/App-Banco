package com.server.api.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = {"numero_identificacion", "correo"}))
public class Usuario { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "identificacion_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_identificacion"))
    private ValoresLista identificacion;

    @Column(name = "numero_identificacion", nullable = false, length = 50)
    private String numeroIdentificacion;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombres;

    @NotBlank(message = "El campo correo no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String telefono;

    @Column(nullable = false, length = 255)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "ciudad_residencia_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_ciudad_residencia"))
    private Ciudad ciudadResidencia;

    @ManyToOne
    @JoinColumn(name = "profesion_id",
                foreignKey = @ForeignKey(name = "fk_profesion"))
    private ValoresLista profesion;

    @ManyToOne
    @JoinColumn(name = "tipo_trabajo_id",
                foreignKey = @ForeignKey(name = "fk_tipo_trabajo"))
    private ValoresLista tipoTrabajo;

    @ManyToOne
    @JoinColumn(name = "estado_civil_id",
                foreignKey = @ForeignKey(name = "fk_estado_civil"))
    private ValoresLista estadoCivil;

    @ManyToOne
    @JoinColumn(name = "nivel_educativo_id",
                foreignKey = @ForeignKey(name = "fk_nivel_educativo"))
    private ValoresLista nivelEducativo;

    @Column(precision = 15, scale = 2)
    private BigDecimal ingresos;

    @Column(precision = 15, scale = 2)
    private BigDecimal egresos;

    @ManyToOne
    @JoinColumn(name = "genero_id",
                foreignKey = @ForeignKey(name = "fk_genero"))
    private ValoresLista genero;

    @Column(nullable = false)
    private Boolean habilitado = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaBancaria> cuentasBancarias;

    @ManyToMany
    @JoinTable(name = "usuario_sede", 
               joinColumns = @JoinColumn(name = "usuario_id"), 
               inverseJoinColumns = @JoinColumn(name = "sede_id"))
    private List<Sede> sedes;
}
