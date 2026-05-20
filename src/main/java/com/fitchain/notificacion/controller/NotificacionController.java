package com.fitchain.notificacion.controller;

import com.fitchain.notificacion.dto.NotificacionRequestDTO;
import com.fitchain.notificacion.dto.NotificacionResponseDTO;
import com.fitchain.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<NotificacionResponseDTO> crear(@Valid @RequestBody NotificacionRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(notificacionService.obtenerTodas());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.obtenerPorId(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(notificacionService.obtenerPorCliente(clienteId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<NotificacionResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(notificacionService.obtenerPorEstado(estado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequestDTO requestDTO) {
        return ResponseEntity.ok(notificacionService.actualizar(id, requestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
