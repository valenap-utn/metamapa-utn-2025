package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoYaEliminado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.RevisionDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Rol;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IMultimediaRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.ICategoriaRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.IHechoRepositoryJPA;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal.IUsuarioRepositoryJPA;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class HechoServicio implements IHechoServicio {

    private final IHechoRepositoryJPA hechoRepository;
    private final IMultimediaRepository contentMultimediaRepository;
    private final RevisarEstado revisadorHechosSolicitud;
    private final IUsuarioRepositoryJPA userRepository;
    private final ICategoriaRepositoryJPA categoriaRepository;

    public HechoServicio(IHechoRepositoryJPA hechoRepository, IMultimediaRepository contentMultimediaRepository, RevisarEstado revisadorHechosSolicitud, IUsuarioRepositoryJPA userRepository, ICategoriaRepositoryJPA categoriaRepository) {
        this.hechoRepository = hechoRepository;
        this.contentMultimediaRepository = contentMultimediaRepository;
        this.revisadorHechosSolicitud = revisadorHechosSolicitud;
      this.userRepository = userRepository;
      this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Hecho crearHecho(HechoDTODinamica input, MultipartFile contenidoMultimedia) {
        Hecho hecho = new Hecho();
        hecho.setCategoria(input.getCategoria());
        hecho.setTitulo(input.getTitulo());
        hecho.setContenido(input.getContenido());
        hecho.setDescripcion(input.getDescripcion());
        hecho.setFechaCarga(LocalDateTime.now());
        hecho.setUbicacion(input.getUbicacion());
        hecho.setFechaAcontecimiento(input.getFechaAcontecimiento());
        UsuarioDTO usuarioDTO = input.getUsuario();
        Usuario usuario = null;
        if (usuarioDTO != null && usuarioDTO.getId() != null) {
           usuario = this.getOrSaveUsuario(usuarioDTO);
        }

        hecho.setUsuario(usuario);
        if (contenidoMultimedia != null ) {
            ContenidoMultimedia contMultimediaHecho = this.contentMultimediaRepository.saveFile(contenidoMultimedia);
            hecho.setContenidoMultimedia(contMultimediaHecho);
        }
        hecho.setEstado(Estado.EN_REVISION);
        return hechoRepository.save(hecho);
    }

    @Override
    public List<Hecho> obtenerHechosPublicos(Boolean pendientes, Long idUsuario) {
        if (pendientes != null && pendientes) {
            return hechoRepository.findHechosByEstado(Estado.EN_REVISION);
        }
        if (idUsuario != null) {
            return hechoRepository.findHechosByIdUsuario(idUsuario);
        }
        return this.hechoRepository.findAll();
    }

    private Usuario getOrSaveUsuario(UsuarioDTO usuarioDTO) {
      Usuario usuario = this.userRepository.findById(usuarioDTO.getId()).orElse(
              this.userRepository.save(Usuario.builder().id(usuarioDTO.getId()).email(usuarioDTO.getEmail())
                      .nombre(usuarioDTO.getNombre()).apellido(usuarioDTO.getApellido()).build())
      );
      usuario.cargarRolYPermisos(usuarioDTO.getRol(), usuarioDTO.getPermisos());
      return usuario;
    }

    @Override
    public Hecho revisarHecho(Long id, RevisionDTO revisionDTO) {
        Hecho hecho = hechoRepository.findById(id).orElseThrow(() -> new HechoNoEncontrado("Hecho no encontrado"));
        if (revisionDTO.getIdUsuario() == null) {
          throw new UsuarioNoEncontrado("El usuario suministrado no existe");
        }
        Usuario usuario = this.getOrSaveUsuario(revisionDTO.getUsuario());
        if (!usuario.tienePermisoDe(Permiso.REVISARHECHO, Rol.ADMINISTRADOR)) {
            throw new UsuarioSinPermiso("El usuario suministrado no tiene permiso para revisar el hecho");
        }
        Estado estado= this.revisadorHechosSolicitud.revisar(hecho, revisionDTO.getEstado(), revisionDTO.getComentario());
        if (estado == Estado.ACEPTADA) {
            hecho.setFechaAprobacion(LocalDateTime.now());
        }
        return hechoRepository.save(hecho);
    }

    @Override
    public Hecho marcarComoEliminado(Long id) {
        Optional<Hecho> hechoOpt = hechoRepository.findById(id);

        if (hechoOpt.isEmpty()) {
            throw new HechoNoEncontrado("Hecho de id: "+ id +" no encontrado");
        }

        Hecho hecho = hechoOpt.get();
        if (hecho.isEliminado()) {
            throw new HechoYaEliminado("Hecho de id: "+ id +" ya eliminado");
        }
        hecho.setEliminado(true);
        hechoRepository.save(hecho);
        return hecho;
    }

    @Override
    public Hecho findHechoById(Long idHecho) {
        return this.hechoRepository.findById(idHecho).orElseThrow(() -> new HechoNoEncontrado("El hecho con id: " + idHecho + " no fue encontrado"));
    }

    @Override
    public List<Categoria> findAllCategorias() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public List<Hecho> obtenerHechosNuevos(String estadoRaw) {
        // Normalizamos el string que vino por query param
        String estado = estadoRaw == null ? "" : estadoRaw.trim().toUpperCase();
        List<Estado> estadosModerables = List.of(
            Estado.EN_REVISION,
            Estado.ACEPTADA,
            Estado.RECHAZADA
        );
        if (estado.isEmpty() || "TODAS".equals(estado) || "TODOS".equals(estado)) {
            return hechoRepository.findHechosByEstadoIn(estadosModerables);
        }
        try {
            Estado estadoFinal = Estado.valueOf(estado);
            return hechoRepository.findHechosByEstado(estadoFinal);
        } catch (IllegalArgumentException e) {
            log.info("[HechoServicio] Valor inválido para estado='" + estado + "', devolviendo todos los estados moderables.");
            return hechoRepository.findHechosByEstadoIn(estadosModerables);
        }
    }

}
