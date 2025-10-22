package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.model.dtos.ConjuntoEstadisticasDTO;
import ar.edu.utn.frba.dds.model.dtos.EstadisticaDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache {
    private ConjuntoEstadisticasDTO cache;

    public void actualizar(ConjuntoEstadisticasDTO resultados) {
        this.cache = resultados;
    }

    public ConjuntoEstadisticasDTO obtener() {
        return cache;
    }
}
