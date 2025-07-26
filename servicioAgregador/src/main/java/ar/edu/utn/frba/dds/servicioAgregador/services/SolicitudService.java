package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudError;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.DetectorDeSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IFuenteEstaticaDinamicaRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechosExternosRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.ISolicitudRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.UserRepository;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService implements ISolicitudService {
    private final FactoryDetectorDeSpam detectorDeSpamFactory;
    private final ISolicitudRepository repo;
    private final IHechoRepository hechoRepository;
    private final UserRepository userRepository;
    private final IHechosExternosRepository idHechosExternos;

    public SolicitudService(ISolicitudRepository repo, FactoryDetectorDeSpam detectorDeSpamFactory, IHechoRepository hechoRepository, UserRepository userRepository, IHechosExternosRepository idHechosExternos) {
        this.repo = repo;
        this.detectorDeSpamFactory = detectorDeSpamFactory;
      this.hechoRepository = hechoRepository;
      this.userRepository = userRepository;
      this.idHechosExternos = idHechosExternos;
    }

    public SolicitudOutputDTO crearSolicitud(SolicitudInputDTO solicitudInput) {
        Hecho hecho = this.hechoRepository.findById(solicitudInput.getIdHecho());
        Usuario user = this.userRepository.findById(solicitudInput.getIdusuario());
        Solicitud solicitud = new Solicitud(hecho, user, solicitudInput.getJustificacion());
        DetectorDeSpam detectorDeSpam = this.detectorDeSpamFactory.crearDetectorDeSpamBasico();
        //se decide modelar la deteccion del spam que se realice antes de agregar la solicitud al repositorio
        if (detectorDeSpam.esSpam(solicitud.getJustificacion())) {
            solicitud.marcarComoSpam();
            solicitud.rechazar();
            throw new SolicitudError("Justificacion Con Spam");
        }

        repo.save(solicitud);
        return this.toSolicitudOutputDTO(solicitud);
    }


    private SolicitudOutputDTO toSolicitudOutputDTO(Solicitud solicitud) {
        SolicitudOutputDTO solicitudOutputDTO = new SolicitudOutputDTO();
        solicitudOutputDTO.setIdHecho(solicitud.getHecho().getId());
        solicitudOutputDTO.setJustificacion(solicitud.getJustificacion());
        solicitudOutputDTO.setIdusuario(solicitud.getUsuario().getId());
        return solicitudOutputDTO;
    }

    public void aceptarSolicitud(Solicitud solicitud) {
        solicitud.aceptar();
        Hecho hechoAEliminar = solicitud.getHecho();
        hechoAEliminar.setEliminado(true);
        ClientFuente client = FactoryClientFuente.getClientPorOrigen(hechoAEliminar.getOrigen());
        client.postEliminado(hechoAEliminar, this.idHechosExternos.findIDFuente(hechoAEliminar.getId()));
        repo.save(solicitud);
    }





}
