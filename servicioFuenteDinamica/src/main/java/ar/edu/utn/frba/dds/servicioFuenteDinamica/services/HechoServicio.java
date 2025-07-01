package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.RevisarEstado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.HechoRepository;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.IHechoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HechoServicio implements IHechoServicio {

    @Autowired
    private IHechoRepository hechoRepository;
    private RevisarEstado revisadorHechosSolicitud;
    @Override
    public Hecho crearHecho(HechoDTODinamica input, MultipartFile contenidoMultimedia) {
        Hecho hecho = new Hecho();
        hecho.setDescripcion(input.getDescripcion());
        hecho.setEsAnonimo(input.isEsAnonimo());
        hecho.setFechaCarga(LocalDate.now());
        if (!contenidoMultimedia.isEmpty()) {
            hecho.setContenidoMultimedia(contenidoMultimedia);
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
