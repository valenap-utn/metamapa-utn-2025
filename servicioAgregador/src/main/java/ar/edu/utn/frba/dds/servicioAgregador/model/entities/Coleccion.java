package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.exceptions.ColeccionConDatosErroneos;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.TodosConsensuados;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroNoEstaEliminado;

import java.util.*;
import java.util.stream.Stream;

import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.converters.AlgoritmoConsensoConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "coleccion")
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "coleccion_id")
    private final List<FuenteColeccion> fuenteColeccions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "coleccion_id")
    private final List<Filtro> criteriosDePertenencia;

    @Convert(converter = AlgoritmoConsensoConverter.class)
    @Column(name = "algoritmo_consenso")
    private AlgoritmoConsenso algoritmoConsenso;
    @Column(name = "eliminada")
    private Boolean eliminada = Boolean.FALSE;
    public Coleccion() {
        this.fuenteColeccions = new ArrayList<>();
        this.criteriosDePertenencia = new ArrayList<>();
    }

    public Coleccion(String titulo, String descripcion, AlgoritmoConsenso algoritmoConsenso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        if (titulo == null || descripcion == null) {
            throw new ColeccionConDatosErroneos("Falta el titulo y/o la descripcion");
        }
        this.fuenteColeccions = new ArrayList<>();
        this.criteriosDePertenencia = new ArrayList<>();

        AlgoritmoConsenso algoritmoElegido = algoritmoConsenso;
        if(algoritmoConsenso == null)
            algoritmoElegido = new TodosConsensuados();
        this.algoritmoConsenso = algoritmoElegido;
    }

    public void agregarCriterios(List<Filtro> filtros) {
        this.criteriosDePertenencia.addAll(filtros);
    }

    public List<Hecho> getHechos() {
        return this.fuenteColeccions.stream().flatMap(this::getHechosPorFuente).toList();

    }

    private Stream<Hecho> getHechosPorFuente(FuenteColeccion fuenteColeccion) {
        return fuenteColeccion.getHechos().stream().filter(this::cumpleTodosLosCriterios);
    }

    public void agregarFuentes(Collection<FuenteColeccion> fuenteColeccions) {
        this.fuenteColeccions.addAll(fuenteColeccions);
    }


    private boolean cumpleTodosLosCriterios(Hecho hecho) {
        return this.criteriosDePertenencia.stream().allMatch(criterio -> criterio.hechoCumple(hecho));
    }

    public void consensuarHechos(Set<FuenteColeccion> fuenteColeccions) {
        this.getHechos().forEach(hecho -> this.consensuarHecho(hecho, fuenteColeccions));
    }

    private void consensuarHecho(Hecho hecho, Set<FuenteColeccion> fuenteColeccions) {
        if(this.getAlgoritmoConsenso().consensuarHecho(hecho, fuenteColeccions)){
           hecho.agregarAlgoritmo(this.getAlgoritmoConsenso());
        }
    }

    public void actualizarFuentes(List<FuenteColeccion> fuenteColeccions) {
        this.fuenteColeccions.clear();
        this.fuenteColeccions.addAll(fuenteColeccions);
    }

    public void actualizarCriterios(List<Filtro> Criterios ) {
        this.criteriosDePertenencia.clear();
        this.criteriosDePertenencia.addAll(Criterios);
    }

    public void marcarComoEliminada() {
        this.eliminada = Boolean.TRUE;
    }
}

