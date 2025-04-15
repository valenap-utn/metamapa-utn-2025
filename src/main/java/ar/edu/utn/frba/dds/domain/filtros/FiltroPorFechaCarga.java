package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import java.time.LocalDateTime;

public class FiltroPorFechaCarga extends FiltroPorFecha {
    public FiltroPorFechaCarga(LocalDateTime desde, LocalDateTime hasta) {
        super(desde, hasta);
    }

  @Override
    protected LocalDateTime obtenerUnTipoFecha(Hecho unHecho){
        return unHecho.getFechaCarga();
    }
}
