package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class MapperFuenteClient {
  public abstract List<Hecho> toHechos(WebClient.ResponseSpec respuesta, String url);

  protected Hecho.HechoBuilder crearHechoBasico(HechoDTO hechoDTO) {
    return Hecho.builder()
            .id(hechoDTO.getId())
            .titulo(hechoDTO.getTitulo())
            .descripcion(hechoDTO.getDescripcion())
            .fechaCarga(hechoDTO.getFechaCarga())
            .fechaAcontecimiento(hechoDTO.getFechaAcontecimiento())
            .categoria(hechoDTO.getCategoria())
            .ubicacion(hechoDTO.getUbicacion());
  }
}
