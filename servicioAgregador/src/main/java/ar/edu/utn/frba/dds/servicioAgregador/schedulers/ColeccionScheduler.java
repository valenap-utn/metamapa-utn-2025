package ar.edu.utn.frba.dds.servicioAgregador.schedulers;

import ar.edu.utn.frba.dds.servicioAgregador.services.ColeccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ColeccionScheduler {
  private final ColeccionService coleccionService;

  public ColeccionScheduler(ColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @Scheduled(cron = "0 0 * * * *")
  public void actualizarFuentes() {
    coleccionService.actualizarHechosColecciones()
            .doOnSuccess(v -> System.out.println("Salio bien la actualizacion"))
            .doOnError(e -> System.out.println("Error en la actualizacion"))
            .subscribe();
  }
}
