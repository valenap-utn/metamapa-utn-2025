package ar.edu.utn.frba.dds.domain.filtros;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FiltroPorFechaCarga extends FiltroPorFecha {
    public FiltroPorFechaCarga(LocalDate desde, LocalDate hasta) {
        super(desde, hasta);
    }

  @Override
    protected LocalDate obtenerUnTipoFecha(Hecho unHecho){
        return unHecho.getFechaCarga();
    }
}
