package ar.edu.utn.frba.dds.servicioEstadistica.utils;

import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ColeccionProvinciaMayorHechos extends CalculadorEstadisticas{

  protected DatoEstadisticoDTO formarEstadistica(String provincia, String coleccion, Long cantidad) {
    DatoEstadisticoDTO estadisticoDTO = new DatoEstadisticoDTO();
    estadisticoDTO.setCantidad(cantidad);
    estadisticoDTO.setPrimerCriterio(coleccion);
    estadisticoDTO.setSegundoCriterio(provincia);
    return estadisticoDTO;
  }

  @Override
  protected List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo) {
    List<String> titulosColeccion = datoCalculo.getTitulosColeccion();
    List<Hecho> hechos = datoCalculo.getHechos().stream().filter(hecho -> hecho.getProvincia() != null).toList();
    List<MapeoColeccion> datos =
            titulosColeccion.stream().map(
                    titulo -> {
                      Map<String, Long> datoshechos = hechos.stream().collect(Collectors.groupingBy(
                              Hecho::getProvincia,
                              Collectors.counting()
                      ));
                      return new MapeoColeccion(titulo, datoshechos);
                    }
            ).toList();
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    datos.forEach( mapeo -> mapeo.getHechosMapeados().forEach(
            (provincia, cantidad) -> datosObtenidos.add(this.formarEstadistica(provincia, mapeo.getTitulo(), cantidad))
    ));
    return datosObtenidos;
  }

  @Override
  protected String getNombreEstadistica() {
    return "COLECCIONPROVINCIAMAYORHECHOS";
  }
}
