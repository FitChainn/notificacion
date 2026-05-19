package com.fitchain.notificacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NotificacionRequestDTO {

    @NotNull(message = "El clienteId es obligatorio")
    private Long clienteId;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    private String tipo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @NotNull(message = "La fecha de envío es obligatoria")
    private LocalDate fechaEnvio;
}
