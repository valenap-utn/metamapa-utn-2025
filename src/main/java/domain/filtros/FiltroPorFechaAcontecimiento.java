package domain.filtros;

import ar.edu.utn.frba.dds.Hecho;

import java.time.LocalDate;

public class FiltroPorFechaAcontecimiento implements Filtro {
    private LocalDate desde;
    private LocalDate hasta;

    public FiltroPorFechaAcontecimiento(LocalDate desde, LocalDate hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }

    @Override
    public boolean hechoCumple(Hecho hecho) {
        LocalDate fecha = hecho.getFechaAcontecimiento();
        return (fecha.isEqual(desde) || fecha.isAfter(desde)) &&
                (fecha.isEqual(hasta) || fecha.isBefore(hasta));
    }
}
