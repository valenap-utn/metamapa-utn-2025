package ar.edu.utn.frba.dds.servicioAgregador.schedulers;

import ar.edu.utn.frba.dds.servicioAgregador.services.ColeccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColeccionScheduler {
  private ColeccionService coleccionService;

  public ColeccionScheduler(ColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @Scheduled(cron = "0 0 * * * *")
  public void actualizarFuentes() {
    coleccionService.actualizarHechosColecciones();
  }
}
