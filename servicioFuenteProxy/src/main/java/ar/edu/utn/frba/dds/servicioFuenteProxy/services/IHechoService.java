package ar.edu.utn.frba.dds.servicioFuenteProxy.services;

import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.input.HechoInputDTO;
import ar.edu.utn.frba.dds.servicioFuenteProxy.clients.dtos.output.HechoOutputDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IHechoService {

    void marcarComoEliminado(Long id);

    List<HechoOutputDTO> getHechosExternos(
            String categoria,
            Double latitud,
            Double longitud,
            LocalDateTime fechaReporteDesde,
            LocalDateTime fechaReporteHasta,
            LocalDateTime fechaAcontecimientoDesde,
            LocalDateTime fechaAcontecimientoHasta
    ); // <-- se encarga de hacer la llamada al endpoint de login y devolver un Mono con el token

}
