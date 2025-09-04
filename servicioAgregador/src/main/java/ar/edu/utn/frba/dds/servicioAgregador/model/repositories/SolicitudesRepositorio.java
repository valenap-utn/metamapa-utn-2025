package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudesRepositorio implements ISolicitudRepository{
    private final Map<Long, Solicitud> solicitudes;
    private final AtomicLong idCounter = new AtomicLong(1);

  public SolicitudesRepositorio() {
    this.solicitudes = new HashMap<>();
  }

  @Override
    public Solicitud save(Solicitud solicitud) {
      Long id = solicitud.getId();
      if(id == null) {
          id = idCounter.getAndIncrement();
          solicitud.setId(id);
      }
      return this.solicitudes.put(id, solicitud);
    }

    @Override
    public Solicitud findById(Long id) {
        return this.solicitudes.get(id);
    }

    @Override
    public Set<Solicitud> findAll() {
        return new HashSet<>(this.solicitudes.values());
    }

    public boolean estaEliminado(Hecho hecho) {
        return solicitudes.values().stream().anyMatch(sol -> Objects.equals(sol.getHecho().getId(), hecho.getId()));
    }

    public List<Solicitud> listarSolicitudes() {
        return new ArrayList<>(solicitudes.values());
    }

}
