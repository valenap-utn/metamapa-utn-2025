package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstadisticaDTO {
    private String nombre;                       // "COLECCIONPROVINCIAMAYORHECHOS" | "CATEGORIATOP" | "SOLICITUDESSPAM" | ...
    private List<DatoEstadisticoDTO> datos;
    private Long totalDatos;
}
