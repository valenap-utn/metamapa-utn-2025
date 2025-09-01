package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Solicitud;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class SolicitudRepository implements ISolicitudRepository {
    private final Map<Long, Solicitud> solicitudes = new HashMap<>();
    private long idActual = 1L;

    @Override
    public Solicitud save(Solicitud solicitud) {
        if (solicitud.getId() == null) {
            solicitud.setId(idActual++);
        }
        solicitudes.put(solicitud.getId(), solicitud);
        return solicitud;
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return Optional.ofNullable(solicitudes.get(id));
    }

    @Override
    public List<Solicitud> findByIDHecho(Long id) {
        return this.solicitudes.values().stream().filter(solicitud -> solicitud.getHecho().tieneId(id) ).collect(Collectors.toList());
    }
}

