package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;

import java.time.LocalDateTime;

public class FiltroPorFechaAcontecimiento extends FiltroPorFecha {
    public FiltroPorFechaAcontecimiento(LocalDateTime desde, LocalDateTime hasta) {
        super(desde, hasta);
    }

    @Override
    protected LocalDateTime obtenerUnTipoFecha(Hecho hecho) {
        return hecho.getFechaAcontecimiento();
    }
}
