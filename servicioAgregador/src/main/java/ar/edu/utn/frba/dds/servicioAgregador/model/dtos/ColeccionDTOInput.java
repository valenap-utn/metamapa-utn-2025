package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ColeccionDTOInput {
  private String titulo;
  private String descripcion;
  private UsuarioDTO usuario;
  private List<FuenteDTO> fuentes;
  private List<CriterioDTO> criterios;
  private String algoritmo;

  public Long getIdUsuario() {
    return this.usuario.getId();
  }
}
