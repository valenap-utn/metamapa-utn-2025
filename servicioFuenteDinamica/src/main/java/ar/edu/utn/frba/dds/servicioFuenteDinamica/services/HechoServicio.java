package ar.edu.utn.frba.dds.servicioFuenteDinamica.services;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.HechoDTODinamica;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.EstadoHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.HechoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HechoServicio {

    @Autowired
    private HechoRepository hechoRepository;

    public Hecho crearHecho(HechoDTODinamica input) {
        Hecho hecho = new Hecho();
        hecho.setDescripcion(input.getDescripcion());
        hecho.setEsAnonimo(input.isEsAnonimo());
        hecho.setFechaCarga(LocalDate.now());
        hecho.setEstadoHecho(EstadoHecho.PENDIENTE_REVISION);
        return hechoRepository.save(hecho);
    }

    public List<Hecho> obtenerHechosPublicos() {
        return hechoRepository.findByEstadoIn(List.of(EstadoHecho.ACEPTADO, EstadoHecho.ACEPTADO_CON_CAMBIOS));
    }

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

    public Hecho revisarHecho(Long id, String estadoStr, String comentario) {
        Hecho hecho = hechoRepository.findById(id).orElseThrow(() -> new RuntimeException("Hecho no encontrado"));
        EstadoHecho estado = EstadoHecho.valueOf(estadoStr);
        hecho.setEstadoHecho(estado);
        hecho.setComentarioRevision(comentario);
        return hechoRepository.save(hecho);
    }
}
