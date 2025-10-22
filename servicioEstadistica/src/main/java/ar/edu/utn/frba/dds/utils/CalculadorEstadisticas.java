package ar.edu.utn.frba.dds.utils;


import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CalculadorEstadisticas {
    public EstadisticaDTO calcular(DatoCalculo datoCalculo) {
        EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
        estadisticaDTO.setDatos(this.generarCalculo(datoCalculo));
        estadisticaDTO.setNombre(this.getNombreEstadistica());
        return estadisticaDTO;
    }

    protected abstract List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo);

    protected abstract String getNombreEstadistica();
}
