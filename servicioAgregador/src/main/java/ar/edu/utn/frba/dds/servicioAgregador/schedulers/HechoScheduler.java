package ar.edu.utn.frba.dds.servicioAgregador.schedulers;

import ar.edu.utn.frba.dds.servicioAgregador.services.IEstandarizador;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HechoScheduler {
  private final IEstandarizador estandarizador;

  public HechoScheduler(IEstandarizador estandarizador) {
    this.estandarizador = estandarizador;
  }

  @Scheduled(cron = "${api.cron.normalizarHechos}")
  public void normalizarHechos() {
    this.estandarizador.estandarizarHechos()
            .doOnSuccess(v -> System.out.println("Se han normalizado los hechos"))
            .doOnError(e -> System.out.println("Error al consensuar hechos"))
            .subscribe();
  }
}
