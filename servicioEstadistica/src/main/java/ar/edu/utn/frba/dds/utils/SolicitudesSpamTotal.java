package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.DatoEstadisticoDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import ar.edu.utn.frba.dds.model.entities.DatoCalculo;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.ArrayList;
import java.util.List;

public class SolicitudesSpamTotal extends CalculadorEstadisticas{

  @Override
  protected List<DatoEstadisticoDTO> generarCalculo(DatoCalculo datoCalculo) {
    List<Solicitud> solicitudes = datoCalculo.getSolicitudes();
    Long cantidadSpam = solicitudes.stream()
            .filter(Solicitud::fueMarcadaComoSpam)
            .count();
    Long cantidadTotal = (long) solicitudes.size();
    List<DatoEstadisticoDTO> datosObtenidos = new ArrayList<>();
    DatoEstadisticoDTO datoEstadisticoDTO = new DatoEstadisticoDTO();
    datoEstadisticoDTO.setCantidad(cantidadSpam);
    datoEstadisticoDTO.setTotal(cantidadTotal);
    datosObtenidos.add(datoEstadisticoDTO);
    return datosObtenidos;
  }

  @Override
  protected String getNombreEstadistica() {
    return "SOLICITUDESSPAM";
  }
}
