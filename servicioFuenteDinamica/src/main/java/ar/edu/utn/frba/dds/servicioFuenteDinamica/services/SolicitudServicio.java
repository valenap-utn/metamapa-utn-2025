package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoSolicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.ISolicitudRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.SolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudServicio implements ISolicitudServicio {

    @Autowired
    private ISolicitudRepository solicitudRepository;

    @Autowired
    private IHechoRepository hechoRepository;

    @Override
    public Solicitud crearSolicitud(Long hechoId, Usuario usuario, String contenido) {
        Hecho hecho = hechoRepository.findById(hechoId).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        Solicitud solicitud = new Solicitud(hecho, usuario, contenido);
        solicitud.setHecho(hecho);
        solicitud.setEstado(EstadoSolicitud.EN_REVISION);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitud procesarSolicitud(Long id, String estadoStr, String justificacion) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        EstadoSolicitud estado = EstadoSolicitud.valueOf(estadoStr);
        solicitud.setEstado(estado);
        solicitud.setJustificacion(justificacion);

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
