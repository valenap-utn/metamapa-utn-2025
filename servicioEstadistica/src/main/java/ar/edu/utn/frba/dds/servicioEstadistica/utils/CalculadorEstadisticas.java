package ar.edu.utn.frba.dds.servicioEstadistica.utils;


import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.DatoCalculo;
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
