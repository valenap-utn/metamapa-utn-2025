package ar.edu.utn.frba.dds.servicioFuenteDinamica.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.enums.EstadoHecho;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HechoRepository {
    private final Map<Long, Hecho> hechos = new HashMap<>();
    private long idActual = 1L;

    public List<Hecho> findByEstadoIn(List<EstadoHecho> estados) {
        return hechos.values().stream()
                .filter(hecho -> estados.contains(hecho.getEstadoHecho()))
                .collect(Collectors.toList());
    }

    public Hecho save(Hecho hecho) {
        if (hecho.getId() == null) {
            hecho.setId(idActual++);
        }
        hechos.put(hecho.getId(), hecho);
        return hecho;
    }

    public Optional<Hecho> findById(Long id) {
        return Optional.ofNullable(hechos.get(id));
    }
}
