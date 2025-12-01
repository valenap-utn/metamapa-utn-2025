package ar.edu.utn.frba.dds.utils;


import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.DatoCalculo;
import java.util.*;

public abstract class CalculadorEstadisticas {
    public EstadisticaDTO calcular(DatoCalculo datoCalculo) {
        EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
        List<DatoEstadisticoDTO> datos =this.generarCalculo(datoCalculo);
        estadisticaDTO.setDatos(datos);
        estadisticaDTO.setTotalDatos((long) datos.size());
        estadisticaDTO.setNombre(this.getNombreEstadistica());
        return estadisticaDTO;
    }

    protected abstract List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo);

    protected abstract String getNombreEstadistica();
}
