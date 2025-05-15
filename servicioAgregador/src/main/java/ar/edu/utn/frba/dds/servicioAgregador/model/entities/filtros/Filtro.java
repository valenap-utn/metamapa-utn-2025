package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

public interface Filtro {
    public boolean hechoCumple(Hecho unHecho);
}
