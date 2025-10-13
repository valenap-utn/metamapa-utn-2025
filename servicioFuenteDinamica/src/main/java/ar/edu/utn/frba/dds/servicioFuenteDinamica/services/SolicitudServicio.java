package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.EstadoIncorrecto;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.SolicitudNoEncontrada;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.SolicitudDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.HechoModificacion;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.PermisoRevisarSolicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.ISolicitudRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.IUsuarioRepositoryJPA;
import org.springframework.stereotype.Service;

@Service
public class SolicitudServicio implements ISolicitudServicio {
    private final ISolicitudRepositoryJPA solicitudRepository;
    private final IHechoRepositoryJPA hechoRepository;
    private final RevisarEstado revisadorHechosSolicitud;
    private final IUsuarioRepositoryJPA usuarioRepository;

    public SolicitudServicio(ISolicitudRepositoryJPA solicitudRepository, IHechoRepositoryJPA hechoRepository, RevisarEstado revisadorHechosSolicitud, IUsuarioRepositoryJPA usuarioRepository) {
      this.solicitudRepository = solicitudRepository;
      this.hechoRepository = hechoRepository;
      this.revisadorHechosSolicitud = revisadorHechosSolicitud;
      this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Solicitud crearSolicitud(SolicitudDTO solicitudDTO) {
        Hecho hecho = hechoRepository.findById(solicitudDTO.getIdHecho()).orElseThrow(() -> new HechoNoEncontrado("Hecho no encontrado"));
        if (solicitudDTO.getIdusuario() == null) {
            throw new UsuarioSinPermiso("Solo se le permiten modificar hechos a los usuarios con cuenta");
        }
        Usuario usuario =this.usuarioRepository.findById(solicitudDTO.getIdusuario()).orElse(null);
        if (usuario == null) {
            throw new UsuarioNoEncontrado("Usuario no encontrado");
        }
        if(!hecho.getUsuario().equals(usuario)) {
            throw new UsuarioSinPermiso("El usuario solicitante no coincide con el que creo el hecho");
        }
        Solicitud solicitud = new Solicitud(hecho, usuario, solicitudDTO.getJustificacion());
        solicitud.setHecho(hecho);
        solicitud.setEstado(Estado.EN_REVISION);
        HechoModificacion hechoModificacion = this.getHechoModificacion(solicitudDTO);
        solicitud.setHechoModificacion(hechoModificacion);

        return solicitudRepository.save(solicitud);
    }

    private HechoModificacion getHechoModificacion(SolicitudDTO solicitudDTO) {
        HechoModificacion hechoModificacion = new HechoModificacion();
        hechoModificacion.setContenido(solicitudDTO.getPropuesta().getContenido());
        hechoModificacion.setTitulo(solicitudDTO.getPropuesta().getTitulo());
        hechoModificacion.setDescripcion(solicitudDTO.getPropuesta().getDescripcion());
        hechoModificacion.setCategoria(solicitudDTO.getPropuesta().getCategoria().getNombre());
        hechoModificacion.setUbicacion(solicitudDTO.getPropuesta().getUbicacion());
        hechoModificacion.setFechaAcontecimiento(solicitudDTO.getPropuesta().getFechaAcontecimiento());
        return hechoModificacion;
    }

    @Override
    public Solicitud procesarSolicitud(Long id, RevisionDTO revisionDTO) {
        Solicitud solicitud = solicitudRepository.findById(id).orElseThrow(() -> new SolicitudNoEncontrada("Solicitud no encontrada"));
        Usuario usuario = this.usuarioRepository.findById(revisionDTO.getIdUsuario()).orElseThrow(()-> new UsuarioNoEncontrado("El usuario suministrado no existe"));
        if (!usuario.tienePermiso(new PermisoRevisarSolicitud())) {
            throw new UsuarioSinPermiso("El usuario no tiene permisos para revisar la solicitud");
        }
        if (revisionDTO.getEstado().equals("ACEPTADA_CON_CAMBIOS")) {
            throw new EstadoIncorrecto("El estado seteado no es válido");
        }
        this.revisadorHechosSolicitud.revisar(solicitud, revisionDTO.getEstado(), revisionDTO.getComentario());

        if(solicitud.estaAceptada()) {
            Hecho hecho = this.hechoRepository.findById(solicitud.getIdHecho()).orElse(null);
            if (hecho == null) {
                throw new HechoNoEncontrado("Hecho no encontrado");
            }
            hecho.setTitulo(solicitud.getHechoModificacion().getTitulo());
            hecho.setDescripcion(solicitud.getHechoModificacion().getDescripcion());
            hecho.setUbicacion(solicitud.getHechoModificacion().getUbicacion());
            hecho.setCategoria(new Categoria(solicitud.getHechoModificacion().getCategoria()));
            hecho.setContenido(solicitud.getHechoModificacion().getContenido());
            hecho.setFechaAcontecimiento(solicitud.getHechoModificacion().getFechaAcontecimiento());
            hecho.setContenidoMultimedia(solicitud.getHechoModificacion().getContenidoMultimedia());
            this.hechoRepository.save(hecho);
        }
        return solicitudRepository.save(solicitud);
    }
}
