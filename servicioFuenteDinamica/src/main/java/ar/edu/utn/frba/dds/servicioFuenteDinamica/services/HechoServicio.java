package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.HechoNoEncontrado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.SinSolicitudValida;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.exceptions.UsuarioSinPermiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTOModificacionDinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IMultimediaRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.ISolicitudRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HechoServicio implements IHechoServicio {

    private final IHechoRepository hechoRepository;
    private final IMultimediaRepository contentMultimediaRepository;
    private final RevisarEstado revisadorHechosSolicitud;
    private final IUserRepository userRepository;
    private final ISolicitudRepository solicitudRepository;

    public HechoServicio(IHechoRepository hechoRepository, IMultimediaRepository contentMultimediaRepository, RevisarEstado revisadorHechosSolicitud, IUserRepository userRepository, ISolicitudRepository solicitudRepository) {
        this.hechoRepository = hechoRepository;
        this.contentMultimediaRepository = contentMultimediaRepository;
        this.revisadorHechosSolicitud = revisadorHechosSolicitud;
      this.userRepository = userRepository;
      this.solicitudRepository = solicitudRepository;
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
        Usuario usuario = this.userRepository.findById(input.getIdUsuario());
        if(usuario == null) {
            hecho.setEsAnonimo(true);
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
    public List<Hecho> obtenerHechosPublicos() {
        return hechoRepository.findByEstadoIn(List.of(Estado.ACEPTADA, Estado.ACEPTADA_CON_CAMBIOS));
    }

    @Override
    public Hecho modificarHecho(Long hechoId, HechoDTOModificacionDinamica nuevosDatos) {
        Hecho hecho = hechoRepository.findById(hechoId).orElseThrow(() -> new HechoNoEncontrado("Hecho no encontrado"));
        Usuario usuario = this.userRepository.findById(nuevosDatos.getIdUsuario());
        if(!hecho.getEsAnonimo() && hecho.getUsuario() != usuario) {
            throw new UsuarioSinPermiso("Solo puede modificar un hecho su autor");
        }

        List<Solicitud> solicitudesServicio = this.solicitudRepository.findByIDHecho(hecho.getId());
        List<Solicitud> solicitudesServicioSinUsar = solicitudesServicio.stream().filter( sol -> sol.estaAceptada() && sol.noFueUsada()).toList();

        if(solicitudesServicioSinUsar.isEmpty()) {
           throw new SinSolicitudValida("Se necesita una solicitud de edición aceptada y válida para poder modificar un hecho");
        }
        if (Duration.between(hecho.getFechaCarga(), LocalDateTime.now()).toDays() >= 7) {
            throw new IllegalStateException("Ya no se puede modificar el hecho.");
        }
        hecho.setDescripcion(nuevosDatos.getDescripcion());
        hecho.setContenido(nuevosDatos.getContenido());
        hecho.setFechaAcontecimiento(nuevosDatos.getFechaAcontecimiento());
        hecho.setCategoria(nuevosDatos.getCategoria());
        hecho.setTitulo(nuevosDatos.getTitulo());
        hecho.setUbicacion(nuevosDatos.getUbicacion());
        solicitudesServicioSinUsar.forEach(Solicitud::usar);
        return hechoRepository.save(hecho);
    }

    @Override
    public Hecho revisarHecho(Long id, String estadoStr, String comentario) {
        Hecho hecho = hechoRepository.findById(id).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        this.revisadorHechosSolicitud.revisar(hecho, estadoStr, comentario);
        return hechoRepository.save(hecho);
    }
}
