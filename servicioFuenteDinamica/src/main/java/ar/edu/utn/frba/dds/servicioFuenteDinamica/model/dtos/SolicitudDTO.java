package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SolicitudDTO {
  private Long id;
  private Long idHecho;
  private Long idusuario;
  private String justificacion;
  private String estado;
  private LocalDateTime fechaSolicitud;
  private HechoDTOModificacionDinamica propuesta;
}
