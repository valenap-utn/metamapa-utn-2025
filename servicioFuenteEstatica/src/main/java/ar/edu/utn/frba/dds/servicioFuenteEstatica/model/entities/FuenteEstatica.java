package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.dtos.HechoValueObject;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.mappers.HechoMapper;
import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.ImportadorCSV;

import java.util.HashSet;
import java.util.List;
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
        if (this.hechosAsociados.isEmpty()) {
            Set<String> titulosYaAgregados = new HashSet<>();

            for (HechoValueObject hvo : this.importador.importarHechosDataset()) {
                if (!titulosYaAgregados.contains(hvo.getTitulo())) {
                    Hecho hecho = HechoMapper.toEntity(hvo, Origen.PORDATASET);
                    this.hechosAsociados.add(hecho);
                    titulosYaAgregados.add(hvo.getTitulo());
                }
            }
        }
        return this.hechosAsociados;
    }
}
