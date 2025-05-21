package ar.edu.utn.frba.dds.servicioFuenteDinamica.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class SolicitudRepository {
    private final Map<Long, Solicitud> solicitudes = new HashMap<>();
    private long idActual = 1L;

    public Solicitud save(Solicitud solicitud) {
        if (solicitud.getId() == null) {
            solicitud.setId(idActual++);
        }
        solicitudes.put(solicitud.getId(), solicitud);
        return solicitud;
    }

    public Optional<Solicitud> findById(Long id) {
        return Optional.ofNullable(solicitudes.get(id));
    }
}

