package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "fuente_coleccion")
public class FuenteColeccion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name = "origen_id", nullable = false, referencedColumnName = "id")
    @Getter private final Origen origen;

    @Getter private final Set<Hecho> hechos;
    @ManyToOne(optional = false, fetch= FetchType.LAZY)
    @JoinColumn(name = "coleccion_id", nullable = false)
    @Getter private Coleccion coleccion;

    public FuenteColeccion(Origen origen) {
        this.origen = origen;
        this.hechos = new HashSet<>();
    }

    public void actualizarHechos(Collection<Hecho> hechos) {
        this.hechos.clear();
        this.hechos.addAll(hechos);
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

