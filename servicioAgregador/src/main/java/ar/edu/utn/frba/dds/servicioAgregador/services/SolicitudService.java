package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudError;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoSolicitudesOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.DetectorDeSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.IHechosExternosRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.ISolicitudRepository;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.UserRepository;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.ClientFuente;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService implements ISolicitudService {
    private final FactoryDetectorDeSpam detectorDeSpamFactory;
    private final ISolicitudRepository repo;
    private final IHechoRepository hechoRepository;
    private final UserRepository userRepository;
    private final IHechosExternosRepository idHechosExternos;
    private final FactoryClientFuente clientFuenteFactory;
    public SolicitudService(ISolicitudRepository repo, FactoryDetectorDeSpam detectorDeSpamFactory, IHechoRepository hechoRepository, UserRepository userRepository, IHechosExternosRepository idHechosExternos,
                            FactoryClientFuente clientFuenteFactory) {
        this.repo = repo;
        this.detectorDeSpamFactory = detectorDeSpamFactory;
      this.hechoRepository = hechoRepository;
      this.userRepository = userRepository;
      this.idHechosExternos = idHechosExternos;
      this.clientFuenteFactory = clientFuenteFactory;
    }

    public SolicitudOutputDTO crearSolicitud(SolicitudInputDTO solicitudInput) {
        Hecho hecho = this.hechoRepository.findById(solicitudInput.getIdHecho());
        if(hecho == null) {
            throw new HechoNoEncontrado("El hecho no existe");
        }
        Usuario user = this.userRepository.findById(solicitudInput.getIdusuario());
        Solicitud solicitud = new Solicitud(hecho, user, solicitudInput.getJustificacion());
        DetectorDeSpam detectorDeSpam = this.detectorDeSpamFactory.crearDetectorDeSpamBasico();
        //se decide modelar la deteccion del spam que se realice antes de agregar la solicitud al repositorio

        if (detectorDeSpam.esSpam(solicitud.getJustificacion())) {
            solicitud.marcarComoSpam();
            solicitud.rechazar();
            repo.save(solicitud);
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
        solicitudOutputDTO.setEstado(solicitud.getEstado().name());
        solicitudOutputDTO.setId(solicitud.getId());
        return solicitudOutputDTO;
    }

    public SolicitudOutputDTO aceptarSolicitud(Long idSolicitud) {
        Solicitud solicitud = this.repo.findById(idSolicitud);
        solicitud.aceptar();
        Hecho hechoAEliminar = solicitud.getHecho();
        hechoAEliminar.setEliminado(true);
        ClientFuente client = this.clientFuenteFactory.getClientPorOrigen(hechoAEliminar.getOrigen());
        client.postEliminado(hechoAEliminar, this.idHechosExternos.findIDFuente(hechoAEliminar.getId()));
        repo.save(solicitud);
        return this.toSolicitudOutputDTO(solicitud);
    }

    @Override
    public SolicitudOutputDTO eliminarSolicitud(Long idSolicitud) {
        Solicitud solicitud = this.repo.findById(idSolicitud);
        solicitud.rechazar();
        repo.save(solicitud);
        return this.toSolicitudOutputDTO(solicitud);
    }

    @Override
    public ConjuntoSolicitudesOutput buscarSolicitudes() {
        Set<Solicitud> solicituds = this.repo.findAll();
        List<SolicitudOutputDTO> solicitudesDTO = solicituds.stream().map(this::toSolicitudOutputDTO).toList();
        ConjuntoSolicitudesOutput conjuntoSolicitudesOutput = new ConjuntoSolicitudesOutput();
        conjuntoSolicitudesOutput.setSolicitudes(solicitudesDTO);
        return conjuntoSolicitudesOutput;
    }


}
