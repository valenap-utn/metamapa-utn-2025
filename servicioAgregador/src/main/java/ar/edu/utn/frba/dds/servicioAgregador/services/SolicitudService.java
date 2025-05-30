package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.DetectorDeSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.ISolicitudRepository;

public class SolicitudService {
    private final DetectorDeSpam detectorDeSpam;
    private final ISolicitudRepository repo;

    public SolicitudService(ISolicitudRepository repo, DetectorDeSpam detector) {
        this.repo = repo;
        this.detectorDeSpam = detector;
    }

    public Solicitud crearSolicitud(Hecho hecho, Usuario user, String texto) {
        if (!hecho.getOrigen().permiteSolicitud()) {
            throw new IllegalArgumentException("No es posible solicitar eliminación de este hecho debido a su origen");
        }

        Solicitud solicitud = new Solicitud(hecho, user, texto);

        //se decide modelar la deteccion del spam que se realice antes de agregar la solicitud al repositorio
        if (detectorDeSpam.esSpam(texto)) {
            solicitud.marcarComoSpam();
            solicitud.rechazar();
        }

        repo.save(solicitud);
        return solicitud;

    }

    public void aceptarSolicitud(Solicitud solicitud) {
        solicitud.aceptar();
        solicitud.getHecho().setEliminado(true);
        repo.save(solicitud);
        hecho.save(solicitud.getHecho());

    }





}
