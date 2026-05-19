package com.fitchain.notificacion.config;

import com.fitchain.notificacion.model.Notificacion;
import com.fitchain.notificacion.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final NotificacionRepository notificacionRepository;

    @Override
    public void run(String... args) {
        if (notificacionRepository.count() == 0) {
            log.info("Cargando datos de prueba para Notificacion...");

            LocalDate hoy = LocalDate.now();

            Notificacion n1 = new Notificacion();
            n1.setClienteId(1L);
            n1.setTipo("VENCIMIENTO_MEMBRESIA");
            n1.setMensaje("Tu membresía vence en 3 días");
            n1.setFechaEnvio(hoy);
            n1.setEstado("ENVIADA");

            Notificacion n2 = new Notificacion();
            n2.setClienteId(2L);
            n2.setTipo("CONFIRMACION_PAGO");
            n2.setMensaje("Tu pago fue confirmado exitosamente");
            n2.setFechaEnvio(hoy.minusDays(1));
            n2.setEstado("ENVIADA");

            Notificacion n3 = new Notificacion();
            n3.setClienteId(3L);
            n3.setTipo("RECORDATORIO_RESERVA");
            n3.setMensaje("Tienes una reserva mañana a las 10:00");
            n3.setFechaEnvio(hoy);
            n3.setEstado("PENDIENTE");

            Notificacion n4 = new Notificacion();
            n4.setClienteId(1L);
            n4.setTipo("VENCIMIENTO_MEMBRESIA");
            n4.setMensaje("Tu membresía ha vencido");
            n4.setFechaEnvio(hoy.minusDays(5));
            n4.setEstado("ENVIADA");

            notificacionRepository.saveAll(List.of(n1, n2, n3, n4));
            log.info("Datos de prueba cargados: {} notificaciones", notificacionRepository.count());
        } else {
            log.info("Ya existen datos en la base de datos, omitiendo inicialización");
        }
    }
}
