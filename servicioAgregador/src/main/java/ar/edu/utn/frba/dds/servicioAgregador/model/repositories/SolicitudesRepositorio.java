package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class SolicitudesRepositorio implements ISolicitudRepository{
    List<Solicitud> solicitudes = new ArrayList<>();

    @Override
    public Solicitud save(Solicitud solicitud) {
        return null;
    }

    @Override
    public Solicitud findById(Long id) {
        return null;
    }

    @Override
    public Set<Solicitud> findAll() {
        return Set.of();
    }

    public void agregar(Solicitud solicitud) {
        solicitudes.add(solicitud);
    }

    public boolean estaEliminado(Hecho hecho) {
        return solicitudes.stream().anyMatch(sol -> sol.getHecho().getTitulo().equals(hecho.getTitulo()) && sol.esAceptada());
    }

    public List<Solicitud> listarSolicitudes() {
        return solicitudes;
    }

}
