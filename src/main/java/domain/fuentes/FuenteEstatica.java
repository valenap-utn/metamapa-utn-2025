package domain.fuentes;

import ar.edu.utn.frba.dds.Hecho;

import java.util.HashSet;
import java.util.Set;

public class FuenteEstatica implements Fuente {
    private String pathCsv;

    public FuenteEstatica(String pathCsv) {
        this.pathCsv = pathCsv;
    }

    @Override
    public Set<Hecho> obtenerHechos() {
        // Supongamos que usamos OpenCSV o similar para la lectura
        // Se omite la implementación de la lectura real por ahora
        return new HashSet<>();
    }
}
