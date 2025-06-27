package ar.edu.utn.frba.dds.servicioAgregador.schedulers;

import ar.edu.utn.frba.dds.servicioAgregador.services.ColeccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConsensoScheduler {
  private final ColeccionService coleccionService;

  public ConsensoScheduler(ColeccionService coleccionService) {
    this.coleccionService = coleccionService;
  }

  @Scheduled(cron = "0 0 3 * * *")
  public void consensuarHechos() {
    this.coleccionService.consensuarHechos()
        .doOnSuccess(v -> System.out.println("Los hechos"))
        .doOnError(e -> System.out.println("Error al consensuar hechos"))
        .subscribe();
  }
}
