package ar.edu.utn.frba.dds.servicioAgregador.services.mappers;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class MapFiltroDTO {
  public FiltroDTO toFiltroDTO(String categoria, LocalDate fecha_reporte_desde, LocalDate fecha_reporte_hasta,
                                LocalDate fecha_acontecimiento_desde, LocalDate fecha_acontecimiento_hasta,
                                Float latitud, Float longitud, Boolean curada, Boolean entiemporeal) {
    FiltroDTO filtro = new FiltroDTO();
    filtro.setCategoria(categoria);
    filtro.setFecha_reporte_desde(fecha_reporte_desde);
    filtro.setFecha_reporte_hasta(fecha_reporte_hasta);
    filtro.setFecha_acontecimiento_desde(fecha_acontecimiento_desde);
    filtro.setFecha_acontecimiento_hasta(fecha_acontecimiento_hasta);
    filtro.setLatitud(latitud);
    filtro.setLongitud(longitud);
    filtro.setCurada(curada);
    filtro.setEntiemporeal(entiemporeal);
    return  filtro;
  }
}
