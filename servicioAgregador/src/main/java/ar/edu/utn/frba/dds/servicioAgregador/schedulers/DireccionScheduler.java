package ar.edu.utn.frba.dds.servicioAgregador.schedulers;

import ar.edu.utn.frba.dds.servicioAgregador.services.IDireccionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DireccionScheduler {
  public final IDireccionService direccionService;

  public DireccionScheduler(IDireccionService direccionService) {
    this.direccionService = direccionService;
  }

  @Scheduled(cron = "${api.cron.actualizarDirecciones}")
  public void actualizarDirecciones() {
    this.direccionService.actualizarDirecciones()
            .doOnSuccess(v -> System.out.println("Las direcciones se han actualizado"))
            .doOnError(e -> System.out.println("Error al intentar obtener nuevas direcciones"))
            .subscribe();
  }


}
