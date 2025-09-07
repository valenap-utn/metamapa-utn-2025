package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.TodosConsensuados;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroNoEstaEliminado;

import java.util.*;
import java.util.stream.Stream;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.ColeccionFuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.fuente.Fuente;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Setter
@Getter
@Entity
@Table(name = "coleccion", uniqueConstraints = @UniqueConstraint(columnNames = "titulo"))
public class Coleccion {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column
    private UUID id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Size(max = 2000)
    @Column(length = 2000, nullable = false)
    private String descripcion;

    @Transient // no se persiste en la BBDD por ahora, es para q no se queje
    private final List<Fuente> fuentes;
    @Transient
    private final List<Filtro> criteriosDePertenencia;

    // por ahora guardo el nombre del algoritmo como texto
    @Transient
    private AlgoritmoConsenso algoritmoConsenso;

    @OneToMany(mappedBy = "coleccion",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ColeccionFuente> fuentesConfiguradas = new ArrayList<>();
    public Coleccion() {
        this.fuentes = new ArrayList<>();
        this.criteriosDePertenencia = new ArrayList<>();
    }

    public Coleccion(String titulo, String descripcion, AlgoritmoConsenso algoritmoConsenso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fuentes = new ArrayList<>();
        this.criteriosDePertenencia = new ArrayList<>();
        this.criteriosDePertenencia.add(new FiltroNoEstaEliminado());

        AlgoritmoConsenso algoritmoElegido = algoritmoConsenso;
        if(algoritmoConsenso == null)
            algoritmoElegido = new TodosConsensuados();
        this.algoritmoConsenso = algoritmoElegido;
    }

    public void agregarCriterios(List<Filtro> filtros) {
        this.criteriosDePertenencia.addAll(filtros);
    }

    public List<Hecho> getHechos() {
        return this.fuentes.stream().flatMap(this::getHechosPorFuente).toList();

    }

    private Stream<Hecho> getHechosPorFuente(Fuente fuente) {
        return fuente.getHechos().stream().filter(this::cumpleTodosLosCriterios);
    }

    public void agregarFuentes(Collection<Fuente> fuentes) {
        this.fuentes.addAll(fuentes);
    }


    private boolean cumpleTodosLosCriterios(Hecho hecho) {
        return this.criteriosDePertenencia.stream().allMatch(criterio -> criterio.hechoCumple(hecho));
    }

    public void consensuarHechos(Set<Fuente> fuentes) {
        this.getHechos().forEach(hecho -> this.consensuarHecho(hecho, fuentes));
    }

    private void consensuarHecho(Hecho hecho, Set<Fuente> fuentes) {
        if(this.getAlgoritmoConsenso().consensuarHecho(hecho, fuentes)){
           hecho.agregarAlgoritmo(this.getAlgoritmoConsenso());
        }
    }

    public void actualizarFuentes(List<Fuente> fuentes) {
        this.fuentes.clear();
        this.fuentes.addAll(fuentes);
    }

    public void actualizarCriterios(List<Filtro> Criterios ) {
        this.criteriosDePertenencia.clear();
        this.criteriosDePertenencia.addAll(Criterios);
    }
}

