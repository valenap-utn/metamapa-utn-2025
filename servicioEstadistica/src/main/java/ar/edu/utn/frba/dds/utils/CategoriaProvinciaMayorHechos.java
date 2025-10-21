package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaProvinciaMayorHechos extends CalculadorPorDobleCriterioString{

  @Override
  public EstadisticaDTO calcular(List<Hecho> hechos){
    Map<String, Map<String, Long>> datos = hechos.stream()
            .collect(Collectors.groupingBy(
                    h -> h.getCategoria().getNombre() ,
                    Collectors.groupingBy(
                            h -> h.getUbicacion().getProvincia(),
                            Collectors.counting()
                    )
            ));

    EstadisticaDTO estadisticaDTO = this.calcularEstadistica(datos);
    estadisticaDTO.setNombre("CATEGORIAPROVINCIAMAYORHECHOS");
    return estadisticaDTO;
  }

  @Override
  protected DatoEstadisticoDTO formarEstadistica(String categoria, String provincia, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setPrimerCriterio(categoria);
    estadisticoDTO.setSegundoCriterio(provincia);
    estadisticoDTO.setCantidad(cantidad);
    return estadisticoDTO;
  }
}
