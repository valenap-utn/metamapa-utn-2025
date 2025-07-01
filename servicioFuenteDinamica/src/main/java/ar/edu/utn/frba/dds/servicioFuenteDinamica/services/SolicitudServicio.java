package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.ISolicitudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolicitudServicio implements ISolicitudServicio {

    @Autowired
    private ISolicitudRepository solicitudRepository;

    @Autowired
    private IHechoRepository hechoRepository;
    private RevisarEstado revisadorHechosSolicitud;

    @Override
    public Solicitud crearSolicitud(Long hechoId, Usuario usuario, String contenido) {
        Hecho hecho = hechoRepository.findById(hechoId).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        Solicitud solicitud = new Solicitud(hecho, usuario, contenido);
        solicitud.setHecho(hecho);
        solicitud.setEstado(Estado.EN_REVISION);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitud procesarSolicitud(Long id, String estadoStr, String justificacion) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));
        Estado estado = this.revisadorHechosSolicitud.revisar(solicitud, estadoStr, justificacion);

        if (estado != Estado.RECHAZADA) {
            Hecho hecho = solicitud.getHecho();
            hecho.setEstado(
                estado == Estado.ACEPTADA ? Estado.ACEPTADA : Estado.ACEPTADA_CON_CAMBIOS
            );
            hechoRepository.save(hecho);
        }

        return solicitudRepository.save(solicitud);
    }
}
