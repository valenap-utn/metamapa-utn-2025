package ar.edu.utn.frba.dds.servicioAgregador.services.clients;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.HechoDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

public abstract class MapperFuenteClient {
  public abstract List<Hecho> toHechos(WebClient.ResponseSpec respuesta, String url);

  protected Hecho.HechoBuilder crearHechoBasico(HechoDTO hechoDTO) {
    UsuarioDTO usuarioDTO = hechoDTO.getUsuario();
    return Hecho.builder()
            .idExterno(hechoDTO.getId())
            .titulo(hechoDTO.getTitulo())
            .descripcion(hechoDTO.getDescripcion())
            .fechaCarga(hechoDTO.getFechaCarga())
            .fechaAcontecimiento(hechoDTO.getFechaAcontecimiento())
            .categoria(hechoDTO.getCategoria())
            .ubicacion(hechoDTO.getUbicacion())
            .usuario( hechoDTO.getUsuario() == null ?  null:
                    Usuario.builder().id(usuarioDTO.getId()).nombre(usuarioDTO.getNombre())
                            .apellido(usuarioDTO.getApellido()).email(usuarioDTO.getEmail()).build());
  }

  public void modificarHechoParaEliminacion(Hecho hecho, UriBuilder uriBuilder) {
  }

  public abstract Hecho toHecho(WebClient.ResponseSpec responseDelete, String url);
}
