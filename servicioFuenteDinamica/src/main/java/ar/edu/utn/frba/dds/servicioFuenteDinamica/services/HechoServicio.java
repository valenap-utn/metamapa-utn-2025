package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.ContenidoMultimedia;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IMultimediaRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HechoServicio implements IHechoServicio {

    private final IHechoRepository hechoRepository;
    private final IMultimediaRepository contentMultimediaRepository;
    private final RevisarEstado revisadorHechosSolicitud;

    public HechoServicio(IHechoRepository hechoRepository, IMultimediaRepository contentMultimediaRepository, RevisarEstado revisadorHechosSolicitud) {
        this.hechoRepository = hechoRepository;
        this.contentMultimediaRepository = contentMultimediaRepository;
        this.revisadorHechosSolicitud = revisadorHechosSolicitud;
    }

    @Override
    public Hecho crearHecho(HechoDTODinamica input, MultipartFile contenidoMultimedia) {
        Hecho hecho = new Hecho();
        hecho.setDescripcion(input.getDescripcion());
        hecho.setEsAnonimo(input.isEsAnonimo());
        hecho.setFechaCarga(LocalDate.now());
        if (!contenidoMultimedia.isEmpty()) {
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
    public Hecho modificarHecho(Long hechoId, HechoDTODinamica nuevosDatos) {
        Hecho hecho = hechoRepository.findById(hechoId).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        if (Duration.between(hecho.getFechaCarga(), LocalDateTime.now()).toDays() >= 7) {
            throw new IllegalStateException("Ya no se puede modificar el hecho.");
        }
        hecho.setDescripcion(nuevosDatos.getDescripcion());
        hecho.setEsAnonimo(nuevosDatos.isEsAnonimo());
        hecho.setContenido(nuevosDatos.getContenido());
        return hechoRepository.save(hecho);
    }

    @Override
    public Hecho revisarHecho(Long id, String estadoStr, String comentario) {
        Hecho hecho = hechoRepository.findById(id).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        this.revisadorHechosSolicitud.revisar(hecho, estadoStr, comentario);
        return hechoRepository.save(hecho);
    }
}
