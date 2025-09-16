package ar.edu.utn.frba.dds.services.servicioAgregador;

import ar.edu.utn.frba.dds.model.entities.Hecho;
import ar.edu.utn.frba.dds.model.entities.Solicitud;
import java.util.List;


public interface ServicioAgregador {
    List<Hecho> obtenerHechos();
    List<Solicitud> obtenerSolicitudes();
}

