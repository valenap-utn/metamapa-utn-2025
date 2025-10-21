package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class CalculadorPorDobleCriterioString implements CalculadorEstadisticas{
  protected EstadisticaDTO calcularEstadistica(Map<String, Map<String, Long>> datos) {
    EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach((key, value) -> value.forEach((key1, value1) -> datosObtenidos.add(this.formarEstadistica(key, key1, value1))));
    estadisticaDTO.setDatos(datosObtenidos);
    return estadisticaDTO;
  }

  protected abstract DatoEstadisticoDTO formarEstadistica(String key, String key1, Long key2);
}
