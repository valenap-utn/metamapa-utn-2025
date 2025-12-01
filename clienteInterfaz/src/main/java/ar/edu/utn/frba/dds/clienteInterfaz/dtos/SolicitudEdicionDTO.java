package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolicitudEdicionDTO {
  private Long id;
  private Long idHecho;
  private Long idusuario;
  private String justificacion;
  private String estado;
  private LocalDateTime fechaSolicitud;
  private HechoDTOInputMultipart propuesta; //cambios propuestos
}
