package ar.edu.utn.frba.dds.servicioEstadistica.utils;

import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Hecho;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaMayorHechos extends CalculadorEstadisticas{

  @Override
  protected List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo) {
    List<Hecho> hechos = datoCalculo.getHechos();
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
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach((key, value) -> datosObtenidos.add(this.formarEstadistica(key, value)));
    return datosObtenidos;
  }

  @Override
  protected String getNombreEstadistica() {
    return "CATEGORIATOP";
  }

  private DatoEstadisticoDTO formarEstadistica(String categoria, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setPrimerCriterio(categoria);
    estadisticoDTO.setCantidad(cantidad);
    return estadisticoDTO;
  }
}
