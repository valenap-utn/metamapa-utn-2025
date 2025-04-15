package ar.edu.utn.frba.dds.domain.fuentes.estaticas;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;
import ar.edu.utn.frba.dds.domain.fuentes.Fuente;

import java.util.Set;

public class FuenteEstatica implements Fuente {
    private ImportadorCSV importador;

    public FuenteEstatica(ImportadorCSV importador) {
        this.importador = importador;
    }

    @Override
    public Set<Hecho> obtenerHechos() {
        ColeccionHechoValueObject hechosDeDataset = this.importador.importarHechosDataset();
        hechosDeDataset.borrarRepetidos();
        Set<Hecho> hechos = hechosDeDataset.getHechos();
        return hechos;
    }
}
