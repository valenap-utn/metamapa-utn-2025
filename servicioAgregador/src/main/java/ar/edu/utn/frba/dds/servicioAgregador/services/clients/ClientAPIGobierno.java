package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.RespuestaAPIGobiernoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Direccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Ubicacion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClientAPIGobierno {
  @Value("${api.url.APIgobierno}")
  private String baseURL;
  private final WebClient webClient;

  public ClientAPIGobierno() {
    this.webClient = WebClient.builder().baseUrl(baseURL).build();
  }

  public Direccion buscarUbicacion(Ubicacion ubicacion) {
    Direccion direccion = new Direccion();
    RespuestaAPIGobiernoDTO respuestaAPIGobiernoDTO = this.webClient.get().uri(uriBuilder -> uriBuilder.path("/ubicacion")
            .queryParam("lat", ubicacion.getLatitud())
            .queryParam("lon", ubicacion.getLongitud())
                    .queryParam("campos", "departamento.nombre,provincia.nombre,gobierno_local.nombre")
            .build()).retrieve().bodyToMono(RespuestaAPIGobiernoDTO.class)
            .block();

    direccion.setProvincia(respuestaAPIGobiernoDTO.getUbicacion().getProvincia().getNombre());
    direccion.setDepartamento(respuestaAPIGobiernoDTO.getUbicacion().getDepartamento().getNombre());
    direccion.setMunicipio(respuestaAPIGobiernoDTO.getUbicacion().getGobierno_local().getNombre());
    return direccion;
  }
}
