package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Solicitud;

import java.util.ArrayList;
import java.util.List;

public abstract class SolicitudesRepositorio implements ISolicitudRepository{
    List<Solicitud> solicitudes = new ArrayList<>();

    public void agregar(Solicitud solicitud) {
        solicitudes.add(solicitud);
    }

    public boolean estaEliminado(Hecho hecho) {
        //return solicitudes.stream().anyMatch(sol -> sol.getHecho().getTitulo().equals(hecho.getTitulo()) && sol.esAceptada());
    }

    public List<Solicitud> listarSolicitudes() {
        return solicitudes;
    }

}
