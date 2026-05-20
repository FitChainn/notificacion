package com.fitchain.notificacion.service;

import com.fitchain.notificacion.WebClient.ClienteClient;
import com.fitchain.notificacion.dto.ClienteDTO;
import com.fitchain.notificacion.dto.NotificacionRequestDTO;
import com.fitchain.notificacion.dto.NotificacionResponseDTO;
import com.fitchain.notificacion.model.Notificacion;
import com.fitchain.notificacion.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final ClienteClient clienteClient;

    private NotificacionResponseDTO toResponseDTO(Notificacion notificacion, ClienteDTO cliente) {
        NotificacionResponseDTO dto = new NotificacionResponseDTO();
        dto.setId(notificacion.getId());
        dto.setTipo(notificacion.getTipo());
        dto.setMensaje(notificacion.getMensaje());
        dto.setFechaEnvio(notificacion.getFechaEnvio());
        dto.setEstado(notificacion.getEstado());
        dto.setCliente(cliente);
        return dto;
    }

    public NotificacionResponseDTO crear(NotificacionRequestDTO requestDTO) {
        log.info("Creando notificación para clienteId {}", requestDTO.getClienteId());

        ClienteDTO cliente = clienteClient.obtenerClientePorId(requestDTO.getClienteId());

        Notificacion notificacion = new Notificacion();
        notificacion.setClienteId(requestDTO.getClienteId());
        notificacion.setTipo(requestDTO.getTipo());
        notificacion.setMensaje(requestDTO.getMensaje());
        notificacion.setFechaEnvio(requestDTO.getFechaEnvio());
        notificacion.setEstado("PENDIENTE");

        Notificacion guardada = notificacionRepository.save(notificacion);
        log.info("Notificación creada con id {}", guardada.getId());
        return toResponseDTO(guardada, cliente);
    }

    public List<NotificacionResponseDTO> obtenerTodas() {
        log.info("Obteniendo todas las notificaciones");
        return notificacionRepository.findAll().stream()
                .map(n -> toResponseDTO(n, clienteClient.obtenerClientePorId(n.getClienteId())))
                .toList();
    }

    public NotificacionResponseDTO obtenerPorId(Long id) {
        log.info("Buscando notificación con id {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Notificación con id " + id + " no encontrada"));
        return toResponseDTO(notificacion, clienteClient.obtenerClientePorId(notificacion.getClienteId()));
    }

    public List<NotificacionResponseDTO> obtenerPorCliente(Long clienteId) {
        log.info("Buscando notificaciones del cliente {}", clienteId);
        ClienteDTO cliente = clienteClient.obtenerClientePorId(clienteId);
        return notificacionRepository.findByClienteId(clienteId).stream()
                .map(n -> toResponseDTO(n, cliente))
                .toList();
    }

    public List<NotificacionResponseDTO> obtenerPorEstado(String estado) {
        log.info("Buscando notificaciones con estado {}", estado);
        return notificacionRepository.findByEstado(estado).stream()
                .map(n -> toResponseDTO(n, clienteClient.obtenerClientePorId(n.getClienteId())))
                .toList();
    }

    public NotificacionResponseDTO actualizar(Long id, NotificacionRequestDTO requestDTO) {
        log.info("Actualizando notificación con id {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Notificación con id " + id + " no encontrada"));

        ClienteDTO cliente = clienteClient.obtenerClientePorId(requestDTO.getClienteId());

        notificacion.setClienteId(requestDTO.getClienteId());
        notificacion.setTipo(requestDTO.getTipo());
        notificacion.setMensaje(requestDTO.getMensaje());
        notificacion.setFechaEnvio(requestDTO.getFechaEnvio());

        Notificacion actualizada = notificacionRepository.save(notificacion);
        log.info("Notificación {} actualizada correctamente", id);
        return toResponseDTO(actualizada, cliente);
    }

    public void eliminar(Long id) {
        log.info("Eliminando notificación con id {}", id);
        if (!notificacionRepository.existsById(id)) {
            throw new NoSuchElementException("Notificación con id " + id + " no encontrada");
        }
        notificacionRepository.deleteById(id);
        log.info("Notificación {} eliminada", id);
    }
}
