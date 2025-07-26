package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import reactor.core.publisher.Mono;


public class Fuente {
    @Getter private final Origen origen;
    @Getter private final Set<Hecho> hechos;

    public Fuente(Origen origen) {
        this.origen = origen;
        this.hechos = new HashSet<>();
    }

    public void agregarHechos(Collection<Hecho> hechos) {
        this.hechos.addAll(hechos);
    }

    public void actualizarHechos(Collection<Hecho> hechos) {
        this.hechos.clear();
        this.agregarHechos(hechos);
    }

    //Métodos para comparar Hechos y poder hacer uso de ellos en los algoritmos de consenso
    public boolean tieneHecho(Hecho hecho) {
        return this.hechos.contains(hecho);
    }

    public boolean tieneOtroHechoConMismoNombrePeroDistintosAtributos(Hecho hecho) {
        return this.hechos.stream()
                        .anyMatch(h ->
                            h.getTitulo().equals(hecho.getTitulo()) && !h.equals(hecho)
                        );
    }

}

