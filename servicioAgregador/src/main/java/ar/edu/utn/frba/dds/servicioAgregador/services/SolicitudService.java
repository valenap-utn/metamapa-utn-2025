package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.DetectorDeSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.ISolicitudRepository;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {
    private final FactoryDetectorDeSpam detectorDeSpamFactory;
    private final ISolicitudRepository repo;

    public SolicitudService(ISolicitudRepository repo, FactoryDetectorDeSpam detectorDeSpamFactory) {
        this.repo = repo;
        this.detectorDeSpamFactory = detectorDeSpamFactory;
    }

    public Solicitud crearSolicitud(Hecho hecho, Usuario user, String texto) {

        Solicitud solicitud = new Solicitud(hecho, user, texto);
        DetectorDeSpam detectorDeSpam = this.detectorDeSpamFactory.crearDetectorDeSpamBasico();
        //se decide modelar la deteccion del spam que se realice antes de agregar la solicitud al repositorio
        if (detectorDeSpam.esSpam(texto)) {
            solicitud.marcarComoSpam();
            solicitud.rechazar();
        } else {
            //TODO: Enviar hecho indicando que no deben devolverlo más
        }

        repo.save(solicitud);
        return solicitud;
    }

    public void aceptarSolicitud(Solicitud solicitud) {
        solicitud.aceptar();
        solicitud.getHecho().setEliminado(true);
        repo.save(solicitud);
    }





}
