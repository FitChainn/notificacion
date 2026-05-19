package com.fitchain.notificacion.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificacionResponseDTO {

    private Long id;
    private String tipo;
    private String mensaje;
    private LocalDate fechaEnvio;
    private String estado;

    private ClienteDTO cliente;
}
