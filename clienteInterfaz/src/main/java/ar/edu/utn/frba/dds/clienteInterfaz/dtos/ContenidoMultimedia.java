package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Data;

@Data
public class ContenidoMultimedia {
  private String nombre;
  private String path;
  private Boolean esVideo = false;
}
