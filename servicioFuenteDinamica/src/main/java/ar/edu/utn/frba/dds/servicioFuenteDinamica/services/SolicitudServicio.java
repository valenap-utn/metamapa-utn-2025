package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.SolicitudNoEncontrada;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.ISolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudServicio implements ISolicitudServicio {
    private final ISolicitudRepository solicitudRepository;
    private final IHechoRepository hechoRepository;
    private final RevisarEstado revisadorHechosSolicitud;

    public SolicitudServicio(ISolicitudRepository solicitudRepository, IHechoRepository hechoRepository, RevisarEstado revisadorHechosSolicitud) {
      this.solicitudRepository = solicitudRepository;
      this.hechoRepository = hechoRepository;
      this.revisadorHechosSolicitud = revisadorHechosSolicitud;
    }

    @Override
    public Solicitud crearSolicitud(Long hechoId, Usuario usuario, String contenido) {
        Hecho hecho = hechoRepository.findById(hechoId).orElseThrow(() -> new HechoNoEncontrado("Hecho no encontrado"));
        Solicitud solicitud = new Solicitud(hecho, usuario, contenido);
        solicitud.setHecho(hecho);
        solicitud.setEstado(Estado.EN_REVISION);
        return solicitudRepository.save(solicitud);
    }

    @Override
    public Solicitud procesarSolicitud(Long id, String estadoStr, String justificacion) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(() -> new SolicitudNoEncontrada("Solicitud no encontrada"));
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
