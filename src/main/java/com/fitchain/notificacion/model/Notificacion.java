package com.fitchain.notificacion.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false)
    private LocalDate fechaEnvio;

    @Column(nullable = false)
    private String estado;
}
