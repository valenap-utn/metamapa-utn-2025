package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaHoraMayorHechos extends CalculadorEstadisticas{

  @Override
  protected List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo) {
    List<Hecho> hechos = datoCalculo.getHechos();
    Map<Integer, Map<String, Long>> datos = hechos.stream()
            .collect(Collectors.groupingBy(h -> h.getFechaAcontecimiento().getHour(),
                    Collectors.groupingBy(
                            h -> h.getCategoria().getNombre(),
                            Collectors.counting()
                    )));
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach((key, value) -> value.forEach((key1, value1) -> datosObtenidos.add(this.formarEstadistica(key, key1, value1))));
    return datosObtenidos;
  }

  @Override
  protected String getNombreEstadistica() {
    return "CATEGORIGAHORAMAYORHECHOS";
  }

  private DatoEstadisticoDTO formarEstadistica(Integer hora, String categoria, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setPrimerCriterio(categoria);
    estadisticoDTO.setHora(hora);
    estadisticoDTO.setCantidad(cantidad);
    return estadisticoDTO;
  }
}
