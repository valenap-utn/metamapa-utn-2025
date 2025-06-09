package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.time.LocalDate;

public class FiltroPorFechaCarga extends FiltroPorFecha {
    public FiltroPorFechaCarga(LocalDate desde, LocalDate hasta) {
        super(desde, hasta);
    }

  @Override
    protected LocalDate obtenerUnTipoFecha(Hecho unHecho){
        return unHecho.getFechaCarga();
    }
}
