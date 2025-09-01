package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HechoRepository implements IHechoRepository {
    private final Map<Long, Hecho> hechos = new HashMap<>();
    private long idActual = 1L;

    @Override
    public List<Hecho> findByEstadoIn(List<Estado> estados) {
        return hechos.values().stream()
                .filter(hecho -> estados.contains(hecho.getEstado()))
                .collect(Collectors.toList());
    }

    @Override
    public Hecho save(Hecho hecho) {
        if (hecho.getId() == null) {
            hecho.setId(idActual++);
        }
        hechos.put(hecho.getId(), hecho);
        return hecho;
    }

    @Override
    public Optional<Hecho> findById(Long id) {
        return Optional.ofNullable(hechos.get(id));
    }
}
