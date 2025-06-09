package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


public class Fuente {
    @Getter
    private final Long id;
    @Getter
    @Setter
    private String Tipo;
    @Getter
    private final Set<Hecho> hechos;
    public Fuente(Long id, String tipo) {
        this.id = id;
        this.Tipo = tipo;
        this.hechos = new HashSet<>();
    }

    public void agregarHechos(Collection<Hecho> hechos) {
        this.hechos.addAll(hechos);
    }

    public void actualizarHechos(Collection<Hecho> hechos) {
        this.hechos.clear();
        this.agregarHechos(hechos);
    }
}

