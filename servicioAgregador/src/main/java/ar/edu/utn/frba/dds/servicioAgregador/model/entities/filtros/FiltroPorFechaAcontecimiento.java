package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.time.LocalDate;

public class FiltroPorFechaAcontecimiento extends FiltroPorFecha {
    public FiltroPorFechaAcontecimiento(LocalDate desde, LocalDate hasta) {
        super(desde, hasta);
    }

    @Override
    protected LocalDate obtenerUnTipoFecha(Hecho hecho) {
        return hecho.getFechaAcontecimiento();
    }
}
