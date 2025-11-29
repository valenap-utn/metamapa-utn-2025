package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoYaEliminado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.FiltroDTODinamica;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
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
    @Value("${api.value.tamanio.pagina}")
    private Integer tamanioPagina;

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
    public List<Hecho> obtenerHechosPublicos(FiltroDTODinamica filtro) {
        if (filtro.getNroPagina() == null || filtro.getNroPagina() < 0) {
            filtro.setNroPagina(0);
        }
        PageRequest pageable = PageRequest.of(filtro.getNroPagina(), this.tamanioPagina);
        if (filtro.getPendientes() != null && filtro.getPendientes()) {
            return hechoRepository.findHechosByEstado(pageable, Estado.EN_REVISION, Boolean.FALSE).getContent();
        }
        if (filtro.getIdUsuario() != null) {
            return hechoRepository.findHechosByIdUsuario(pageable, filtro.getIdUsuario(), Boolean.FALSE).getContent();
        }
        if (filtro.getServicioAgregador() != null && filtro.getServicioAgregador()) {
            List<Hecho> hechos =  this.hechoRepository.findAllByEntregadoAAgregador(pageable, Boolean.FALSE, Boolean.FALSE).getContent();
            hechos.forEach(Hecho::marcarEntregadoAAgregador);
            this.hechoRepository.saveAll(hechos);
            return hechos;
        }
        return this.hechoRepository.findAllByEliminado(pageable, Boolean.FALSE).getContent();
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
        hecho.marcarParaEnviarAgregador();
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
    public List<Hecho> obtenerHechosNuevos(String estadoRaw, Integer nroPagina) {
        // Normalizamos el string que vino por query param
        String estado = estadoRaw == null ? "" : estadoRaw.trim().toUpperCase();
        List<Estado> estadosModerables = List.of(
            Estado.EN_REVISION,
            Estado.ACEPTADA,
            Estado.RECHAZADA
        );
        if (nroPagina == null || nroPagina < 0) {
            nroPagina = 0;
        }
        PageRequest pageable = PageRequest.of(nroPagina, this.tamanioPagina);
        if (estado.isEmpty() || "TODAS".equals(estado) || "TODOS".equals(estado)) {
            return hechoRepository.findHechosByEstadoIn(pageable,estadosModerables, Boolean.FALSE).getContent();
        }
        try {
            Estado estadoFinal = Estado.valueOf(estado);
            return hechoRepository.findHechosByEstado(pageable, estadoFinal, Boolean.FALSE).getContent();
        } catch (IllegalArgumentException e) {
          log.error("[HechoServicio] Valor inválido para estado='{}', devolviendo todos los estados moderables.", estado);
            return hechoRepository.findHechosByEstadoIn(pageable, estadosModerables, Boolean.FALSE).getContent();
        }
    }

}
