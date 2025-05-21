package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.enums.EstadoHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.enums.EstadoSolicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SolicitudServicio {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private HechoRepository hechoRepository;

    public Solicitud crearSolicitud(Long hechoId, Usuario usuario, String contenido) {
        Hecho hecho = (Hecho) hechoRepository.findById(hechoId).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        Solicitud solicitud = new Solicitud(hecho, usuario, contenido);
        solicitud.setHecho(hecho);
        solicitud.setEstado(EstadoSolicitud.EN_REVISION);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud procesarSolicitud(Long id, String estadoStr, Optional<String> justificacion) {
        Solicitud solicitud = (Solicitud) solicitudRepository.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        EstadoSolicitud estado = EstadoSolicitud.valueOf(estadoStr);
        solicitud.setEstado(estado);
        solicitud.setJustificacion(justificacion.orElse(null));

        if (estado != EstadoSolicitud.RECHAZADA) {
            Hecho hecho = solicitud.getHecho();
            hecho.setEstadoHecho(
                    estado == EstadoSolicitud.ACEPTADA ? EstadoHecho.ACEPTADO : EstadoHecho.ACEPTADO_CON_CAMBIOS
            );
            hechoRepository.save(hecho);
        }

        return solicitudRepository.save(solicitud);
    }
}

