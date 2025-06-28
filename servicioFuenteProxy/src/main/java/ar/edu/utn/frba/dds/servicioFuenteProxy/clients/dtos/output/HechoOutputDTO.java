package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HechoOutputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fecha;
    private LocalDateTime fechaCarga;
    private String fuente; // de qué API vino

}
