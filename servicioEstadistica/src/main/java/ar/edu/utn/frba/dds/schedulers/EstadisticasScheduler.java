package ar.edu.utn.frba.dds.schedulers;

import ar.edu.utn.frba.dds.services.ServicioEstadisticas;

import java.util.Timer;
import java.util.TimerTask;

public class EstadisticasScheduler {
    private final ServicioEstadisticas servicio;
    private final Timer timer = new Timer(true);

    public EstadisticasScheduler(ServicioEstadisticas servicio, long intervaloMs) {
        this.servicio = servicio;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                servicio.recalcular();
            }
        }, 0, intervaloMs);
    }
}
