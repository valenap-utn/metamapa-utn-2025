package ar.edu.utn.frba.dds.servicioEstadistica.utils;

import ar.edu.utn.frba.dds.servicioEstadistica.model.dtos.ConjuntoEstadisticasDTO;

public class Cache {
    private ConjuntoEstadisticasDTO cache;

    public void actualizar(ConjuntoEstadisticasDTO resultados) {
        this.cache = resultados;
    }

    public ConjuntoEstadisticasDTO obtener() {
        return cache;
    }
}
