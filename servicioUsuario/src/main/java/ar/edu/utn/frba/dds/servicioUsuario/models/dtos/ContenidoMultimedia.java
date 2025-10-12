package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import lombok.Data;

@Data
public class ContenidoMultimedia {
  private String nombre;
  private String path;
  private Boolean esVideo = false;
}
