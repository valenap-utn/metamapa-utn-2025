package ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.time.LocalDate;
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
