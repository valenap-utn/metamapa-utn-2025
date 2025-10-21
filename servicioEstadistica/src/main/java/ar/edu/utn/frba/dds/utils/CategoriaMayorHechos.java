package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaMayorHechos implements CalculadorEstadisticas{
  @Override
  public EstadisticaDTO calcular(List<Hecho> hechos){
    Map<String, Long> datos = hechos.stream()
            .collect(Collectors.groupingBy(hecho -> hecho.getCategoria().getNombre(), Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
            ));

    EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach((key, value) -> datosObtenidos.add(this.formarEstadistica(key, value)));
    estadisticaDTO.setDatos(datosObtenidos);
    estadisticaDTO.setNombre("CATEGORIATOP");
    return estadisticaDTO;
  }

  private DatoEstadisticoDTO formarEstadistica(String categoria, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setPrimerCriterio(categoria);
    estadisticoDTO.setCantidad(cantidad);
    return estadisticoDTO;
  }
}
