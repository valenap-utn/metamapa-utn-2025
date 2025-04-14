package domain.fuentes;

import ar.edu.utn.frba.dds.Hecho;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class FuenteEstatica implements Fuente {
    private ImportadorCSV importadorCSV;
    public FuenteEstatica(ImportadorCSV importadorCSV) {
        this.importadorCSV = importadorCSV;
    }

    @Override
    public Set<Hecho> obtenerHechos() {
        ListHechoDataset hechosDeDataset = this.importadorCSV.importarHechosDataset();
        hechosDeDataset.borrarRepetidos();
        Set<Hecho> hechos = hechosDeDataset.getHechosNuevos();
        return hechos;
    }
}
