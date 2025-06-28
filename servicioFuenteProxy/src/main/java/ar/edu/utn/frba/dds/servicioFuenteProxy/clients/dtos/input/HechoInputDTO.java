package ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input;

import lombok.Data;

import java.time.LocalDateTime;

// aca va lo que viene de la APi en formato Json

@Data
public class HechoInputDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String categoria;
    private Double latitud;
    private Double longitud;
    private LocalDateTime fecha_hecho;
    private LocalDateTime created_at;
}
