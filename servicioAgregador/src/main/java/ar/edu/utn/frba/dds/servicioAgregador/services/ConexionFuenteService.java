package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.DTOs.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import java.util.Set;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class ConexionFuenteService {
  private final WebClient webClient;
  private final IHechoRepository hechoRepository;

  public void cargarHechosEnFuente(Fuente fuente){
    Set<Hecho> hechos = this.hechoRepository.findByIDFuente(fuente.getId());
    fuente.actualizarHechos(hechos);
  }
  public Mono<Void> actualizarHechosFuente(Fuente fuente) {
    Mono<Fuente> fuenteActualizada = this.setFuenteConHechosAPI(fuente);
    return fuenteActualizada.map(fuenteMono -> {
      this.hechoRepository.saveHechosDeFuente(fuenteMono);
      return Mono.empty();
    }).then();
  }

  ConexionFuenteService(String baseUrl, IHechoRepository hechoRepository) {
    this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    this.hechoRepository = hechoRepository;
  }

  protected Mono<Fuente> setFuenteConHechosAPI(Fuente fuente) {
    return this.mapAFuenteConHechos(
            webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/hechos").build())
            .retrieve(), fuente);
  }

  protected abstract Mono<Fuente> mapAFuenteConHechos(WebClient.ResponseSpec retrieve, Fuente fuente);

  protected Hecho toHecho(HechoDTO hechoDTO) {
    Hecho hecho = new Hecho();
    hecho.setTitulo(hechoDTO.getTitulo());
    hecho.setDescripcion(hechoDTO.getDescripcion());
    hecho.setCategoria(hechoDTO.getCategoria());
    hecho.setUbicacion(hechoDTO.getUbicacion());
    hecho.setFechaAcontecimiento(hechoDTO.getFechaAcontecimiento());
    hecho.setFechaCarga(hechoDTO.getFechaCarga());
    return this.completarHecho(hecho, hechoDTO);
  }

  protected abstract Hecho completarHecho(Hecho hecho, HechoDTO hechoDTO);
}
