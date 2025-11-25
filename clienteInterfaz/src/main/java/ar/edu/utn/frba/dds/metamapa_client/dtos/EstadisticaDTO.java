package ar.edu.utn.frba.dds.metamapa_client.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstadisticaDTO {
    private String clave;                       // "COLECCIONPROVINCIAMAYORHECHOS" | "CATEGORIATOP" | "SOLICITUDESSPAM" | ...
//    private Object datos;
    private List<DatoEstadisticoDTO> datos;
}
