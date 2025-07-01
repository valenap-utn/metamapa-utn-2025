package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoHechoProxy;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapHechoOutput;
import ar.edu.utn.frba.dds.servicioAgregador.services.mappers.MapperAPIClient;
import java.util.List;
import lombok.Getter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class FuenteProxy extends Fuente{
  private final WebClient webClient;
  @Getter
  private final MapperAPIClient mapper;
  private final MapHechoOutput mapHechoOutput;

  public FuenteProxy(Origen origen, String baseUrl, MapperAPIClient mapper, MapHechoOutput mapHechoOutput) {
    super(origen);
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    this.mapper = mapper;
    this.mapHechoOutput = mapHechoOutput;
  }

  @Override
  public Mono<Void> cargarHechosEnFuente(String categoria,
                                         LocalDate fecha_reporte_desde,
                                         LocalDate fecha_reporte_hasta,
                                         LocalDate fecha_acontecimiento_desde,
                                         LocalDate fecha_acontecimiento_hasta,
                                         Float latitud,
                                         Float longitud, List<Hecho> hechos) {
    return this.webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/api/hechos")
            .queryParam("categoria", categoria)
            .queryParam("fecha_reporte_desde", fecha_reporte_desde)
            .queryParam("fecha_reporte_hasta", fecha_reporte_hasta)
            .queryParam("fecha_acontecimiento_desde", fecha_acontecimiento_desde)
            .queryParam("fecha_acontecimiento_hasta", fecha_acontecimiento_hasta)
            .queryParam("latitud", latitud)
            .queryParam("longitud", longitud)
            .build())
        .retrieve().bodyToMono(ConjuntoHechoProxy.class).map(
        response -> {
          Set<Hecho> hechos = response.getHechos().stream().map(this.getMapper()::toHechoFrom).collect(Collectors.toSet());
          this.actualizarHechos(hechos);
          return Mono.empty();
        }).then();
  }

  @Override
  public Mono<Void> actualizarHechosFuente(Fuente _fuente, IHechoRepository _hechoRepository) {
    return Mono.empty();
  }

  @Override
  public void postEliminado(Hecho hecho, Long idHecho) {
    Hecho hechocopia = Hecho.builder().id(idHecho).categoria(hecho.getCategoria())
        .descripcion(hecho.getDescripcion()).fechaAcontecimiento(hecho.getFechaAcontecimiento())
        .eliminado(hecho.isEliminado()).fechaCarga(hecho.getFechaCarga()).build();
    this.webClient.put().uri("/api/hechos/{id}", idHecho)
        .bodyValue(this.mapHechoOutput.toHechoDTO(hechocopia));

  }
}
