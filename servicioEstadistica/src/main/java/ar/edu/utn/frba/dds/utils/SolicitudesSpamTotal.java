package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SolicitudesSpamTotal implements CalculadorSolicitudes{
  @Override
  public EstadisticaDTO calcular(List<Solicitud> solicitudes){
    Long cantidadSpam = solicitudes.stream()
            .filter(Solicitud::fueMarcadaComoSpam)
            .count();
    Long cantidadTotal = (long) solicitudes.size();

    EstadisticaDTO estadisticaDTO = new EstadisticaDTO();
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    DatoEstadisticoDTO datoEstadisticoDTO = new DatoEstadisticoDTO();
    datoEstadisticoDTO.setCantidad(cantidadSpam);
    datoEstadisticoDTO.setTotal(cantidadTotal);
    datosObtenidos.add(datoEstadisticoDTO);
    estadisticaDTO.setDatos(datosObtenidos);
    return estadisticaDTO;
  }

}
