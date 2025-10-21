package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaColeccionMayorHechos extends CalculadorPorDobleCriterioString {
  public EstadisticaDTO calcular(List<Hecho> hechos) {
    Map<String, Map<String, Long>> datos= hechos.stream()
            .collect(Collectors.groupingBy(
                    h -> h.getOrigen().getTipo(),
                    Collectors.groupingBy(
                            h -> h.getUbicacion().getProvincia(),
                            Collectors.counting()
                    )
            ))

            ;
    EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach((key, value) -> value.forEach((key1, value1) -> datosObtenidos.add(this.formarEstadistica(key, key1, value1))));
    estadisticaDTO.setDatos(datosObtenidos);
    return estadisticaDTO;
  }

  protected DatoEstadisticoDTO formarEstadistica(String categoria, String origen, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setCantidad(cantidad);
    estadisticoDTO.setPrimerCriterio(categoria);
    estadisticoDTO.setSegundoCriterio(origen);
    return estadisticoDTO;
  }
}
