package ar.edu.utn.frba.dds.servicioEstadistica.services.servicioAgregador;

import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.ColeccionDTO;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioEstadistica.model.entities.Solicitud;
import java.util.List;


public interface ServicioAgregador {
    List<Hecho> obtenerHechos();
    List<Solicitud> obtenerSolicitudes();
    List<ColeccionDTO> obtenerColecciones();
}

