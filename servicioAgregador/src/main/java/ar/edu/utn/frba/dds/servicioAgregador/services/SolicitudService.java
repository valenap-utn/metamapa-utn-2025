package ar.edu.utn.frba.dds.servicioAgregador.services;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.HechoYaEliminado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudError;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.SolicitudNoEncontrada;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioAgregador.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.ConjuntoSolicitudesOutput;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudInputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.SolicitudOutputDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.deteccionDeSpam.DetectorDeSpam;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.roles.Rol;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.ISolicitudRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal.IUserRepositoryJPA;
import ar.edu.utn.frba.dds.servicioAgregador.services.clients.ClientFuente;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService implements ISolicitudService {
    private final FactoryDetectorDeSpam detectorDeSpamFactory;
    private final ISolicitudRepositoryJPA repo;
    private final IHechoRepositoryJPA hechoRepository;
    private final IUserRepositoryJPA userRepository;
    private final FactoryClientFuente clientFuenteFactory;
    public SolicitudService(ISolicitudRepositoryJPA repo, FactoryDetectorDeSpam detectorDeSpamFactory, IHechoRepositoryJPA hechoRepository, IUserRepositoryJPA userRepository,
                            FactoryClientFuente clientFuenteFactory) {
        this.repo = repo;
        this.detectorDeSpamFactory = detectorDeSpamFactory;
      this.hechoRepository = hechoRepository;
      this.userRepository = userRepository;
      this.clientFuenteFactory = clientFuenteFactory;
    }

    public SolicitudOutputDTO crearSolicitud(SolicitudInputDTO solicitudInput) {
        Hecho hecho = this.hechoRepository.findById(solicitudInput.getIdHecho()).orElse(null);
        if(hecho == null) {
            throw new HechoNoEncontrado("El hecho no existe");
        }

        Usuario user = null;
        if(solicitudInput.getUsuarioId() != null) {
            user = this.getOrSaveUsuario(solicitudInput.getUsuario());
        }
        Solicitud solicitud = new Solicitud(hecho, user, solicitudInput.getJustificacion());
        DetectorDeSpam detectorDeSpam = this.detectorDeSpamFactory.crearDetectorDeSpamBasico();
        //se decide modelar la deteccion del spam que se realice antes de agregar la solicitud al repositorio

        if (detectorDeSpam.esSpam(solicitud.getJustificacion())) {
            solicitud.marcarComoSpam();
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
        solicitudOutputDTO.setUsuario(solicitud.getUsuarioDTO());
        solicitudOutputDTO.setEstado(solicitud.getEstado().name());
        solicitudOutputDTO.setId(solicitud.getId());
        return solicitudOutputDTO;
    }

    @Override
    public SolicitudOutputDTO aceptarSolicitud(Long idSolicitud, RevisionDTO revisionDTO) {
        Solicitud solicitud = revisarSolicitudUsuario(idSolicitud, revisionDTO);

        if(solicitud.noEsPendiente()) {
            throw new SolicitudError("La solicitud no puede ser aceptada, debido a que su estado es: " + solicitud.getEstado().name());
        }
        solicitud.aceptar();
        solicitud.setJustificacionCambioEstado(revisionDTO.getJustificacion());
        Hecho hechoAEliminar = solicitud.getHecho();
        if(hechoAEliminar.isEliminado()) {
            throw new HechoYaEliminado("El hecho ya fue eliminado por otra solicitud");
        }
        hechoAEliminar.setEliminado(true);
        ClientFuente client = this.clientFuenteFactory.getClientPorOrigen(hechoAEliminar.getOrigen());
        client.postEliminado(hechoAEliminar, hechoAEliminar.getIdExterno());
        repo.save(solicitud);
        return this.toSolicitudOutputDTO(solicitud);
    }

    private Solicitud revisarSolicitudUsuario(Long idSolicitud, RevisionDTO revisionDTO) {
        if(revisionDTO.getIdUsuario() == null) {
            throw new UsuarioNoEncontrado("El usuario no existe");
        }
        Usuario user = this.getOrSaveUsuario(revisionDTO.getUsuario());


        if (!user.tienePermisoDe(Permiso.REVISARSOLICITUD, Rol.ADMINISTRADOR)) {
            throw new UsuarioSinPermiso("El Usuario no tiene permiso para eliminar la solicitud");
        }
        Solicitud solicitud = this.repo.findById(idSolicitud).orElse(null);

        if(solicitud == null) {
            throw new SolicitudNoEncontrada("La solicitud con id: " + idSolicitud + " no existe");
        }
        return solicitud;
    }

    @Override
    public SolicitudOutputDTO eliminarSolicitud(Long idSolicitud, RevisionDTO revisionDTO) {
        Solicitud solicitud = revisarSolicitudUsuario(idSolicitud, revisionDTO);

        if(solicitud.noEsPendiente()) {
            throw new SolicitudError("La solicitud no puede ser eliminada, debido a que su estado es: " + solicitud.getEstado().name());
        }
        solicitud.setJustificacionCambioEstado(revisionDTO.getJustificacion());
        solicitud.rechazar();
        repo.save(solicitud);
        return this.toSolicitudOutputDTO(solicitud);
    }

    @Override
    public ConjuntoSolicitudesOutput buscarSolicitudes() {
        List<Solicitud> solicituds = this.repo.findAll();
        List<SolicitudOutputDTO> solicitudesDTO = solicituds.stream().map(this::toSolicitudOutputDTO).toList();
        ConjuntoSolicitudesOutput conjuntoSolicitudesOutput = new ConjuntoSolicitudesOutput();
        conjuntoSolicitudesOutput.setSolicitudes(solicitudesDTO);
        return conjuntoSolicitudesOutput;
    }

    private Usuario getOrSaveUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = this.userRepository.findById(usuarioDTO.getId()).orElse(
                this.userRepository.save(Usuario.builder().id(usuarioDTO.getId()).email(usuarioDTO.getEmail())
                        .nombre(usuarioDTO.getNombre()).apellido(usuarioDTO.getApellido()).build())
        );
        usuario.cargarRolYPermisos(usuarioDTO.getRol(), usuarioDTO.getPermisos());
        return usuario;
    }
}
