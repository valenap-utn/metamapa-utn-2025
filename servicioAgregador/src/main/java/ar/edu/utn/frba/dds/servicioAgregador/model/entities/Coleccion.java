package ar.edu.utn.frba.dds.servicioAgregador.model.entities;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.TodosConsensuados;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.Filtro;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.filtros.FiltroNoEstaEliminado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Coleccion {
    private String id;
    private String titulo;
    private String descripcion;
    private final List<Fuente> fuentes;
    private final List<Filtro> criteriosDePertenencia;
    private AlgoritmoConsenso algoritmoConsenso;

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

    public void agregarCriterios(Filtro ... filtros) {
        this.criteriosDePertenencia.addAll(List.of(filtros));
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
}

