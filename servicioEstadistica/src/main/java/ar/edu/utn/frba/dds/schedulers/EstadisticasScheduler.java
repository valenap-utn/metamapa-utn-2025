package ar.edu.utn.frba.dds.schedulers;

import ar.edu.utn.frba.dds.services.ServicioEstadisticas;

import java.util.Timer;
import java.util.TimerTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EstadisticasScheduler {
    private final ServicioEstadisticas servicio;

    public EstadisticasScheduler(ServicioEstadisticas servicio) {
        this.servicio = servicio;
    }

    @Scheduled(cron = "0 0 */3 * * *")
    public void recalcularEstadisticas(){
        this.servicio.recalcular();
    }
}
