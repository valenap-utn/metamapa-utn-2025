package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicioAgregador {
    List<Hecho> obtenerHechos();
    List<Solicitud> obtenerSolicitudes();
}

