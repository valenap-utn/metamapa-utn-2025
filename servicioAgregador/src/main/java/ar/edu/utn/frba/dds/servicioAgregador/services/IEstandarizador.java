package ar.edu.utn.frba.dds.servicioAgregador.services;

import reactor.core.publisher.Mono;

public interface IEstandarizador {
  Mono<Void> estandarizarHechos();
}
