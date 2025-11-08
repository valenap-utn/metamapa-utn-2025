package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriaProvinciaMayorHechos extends CalculadorEstadisticas{


  @Override
  protected String getNombreEstadistica() {
    return "CATEGORIAPROVINCIAMAYORHECHOS";
  }
  @Override
  protected List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo) {
    List<Hecho> hechos = datoCalculo.getHechos().stream().filter(hecho -> hecho.getProvincia() != null).toList();
    Map<String, Map<String, Long>> datos = hechos.stream()
            .collect(Collectors.groupingBy(
                    h -> h.getCategoria().getNombre() ,
                    Collectors.groupingBy(
                            Hecho::getProvincia,
                            Collectors.counting()
                    )
            ));

    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach((key, value) -> value.forEach((key1, value1) -> datosObtenidos.add(this.formarEstadistica(key, key1, value1))));
    return datosObtenidos;
  }


  private DatoEstadisticoDTO formarEstadistica(String categoria, String provincia, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setPrimerCriterio(categoria);
    estadisticoDTO.setSegundoCriterio(provincia);
    estadisticoDTO.setCantidad(cantidad);
    return estadisticoDTO;
  }
}
