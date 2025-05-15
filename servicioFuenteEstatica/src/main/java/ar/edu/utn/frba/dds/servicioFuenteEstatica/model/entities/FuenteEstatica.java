package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.Hecho;
import ar.edu.utn.frba.dds.domain.entities.colecciones.hechos.ColeccionHechoValueObject;
import ar.edu.utn.frba.dds.domain.fuentes.Fuente;

import java.util.HashSet;
import java.util.Set;

public class FuenteEstatica implements Fuente {
    private ImportadorCSV importador;
    private Set<Hecho> hechosAsociados;

    public FuenteEstatica(ImportadorCSV importador) {
        this.importador = importador;
        this.hechosAsociados = new HashSet<>();
    }

    @Override
    public Set<Hecho> obtenerHechos() {
        if(this.hechosAsociados.isEmpty()){
            ColeccionHechoValueObject hechosDeDataset = this.importador.importarHechosDataset();
            hechosDeDataset.borrarRepetidos();
            this.hechosAsociados = hechosDeDataset.getHechos();
        }
        return this.hechosAsociados;
    }
}
