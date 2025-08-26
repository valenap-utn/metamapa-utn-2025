package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hecho {
    @Getter @Setter private Long id;
    @Setter @Getter private String titulo;
    @Setter @Getter private String descripcion;
    @Setter @Getter private Categoria categoria;
    @Setter @Getter private Ubicacion ubicacion;
    @Setter @Getter private LocalDate fechaAcontecimiento;
    @Setter @Getter private LocalDate fechaCarga;
    @Setter @Getter private Origen origen ;
    @Setter @Getter private boolean eliminado = false;
    @Setter @Getter private ContenidoMultimedia contenidoMultimedia;
    @Getter private final List<AlgoritmoConsenso> algosAceptados = new ArrayList<>();
    @Getter private final Set<String> etiquetas = new HashSet<>();
    private Usuario usuario;

    public void agregarEtiquetas(String ... etiquetas) {
        this.etiquetas.addAll(List.of(etiquetas));
    }

    @Override
    public boolean equals(Object o) { //2 Hecho son "iguales" si tienen mismo titulo y mismos atributos
        if (this == o) return true; //mismo objeto
        if (o == null || getClass() != o.getClass()) return false; //no es un Hecho

        Hecho hecho = (Hecho) o;

        return Objects.equals(titulo, hecho.titulo)
            && Objects.equals(descripcion, hecho.descripcion)
            && Objects.equals(categoria, hecho.categoria)
            && Objects.equals(ubicacion, hecho.ubicacion)
            && Objects.equals(fechaAcontecimiento, hecho.fechaAcontecimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descripcion, categoria, ubicacion, fechaAcontecimiento);
    }

    public void agregarAlgoritmo(AlgoritmoConsenso algoritmoConsenso) {
        this.algosAceptados.add(algoritmoConsenso);
    }

    public boolean estaCuradoPor(AlgoritmoConsenso algoritmoConsenso) {
        return this.algosAceptados.contains(algoritmoConsenso);
    }

    public Long getIdExterno() {
        return this.origen.getIdExterno();
    }

  public Long getIdUsuario() {
        return this.usuario.getId();
  }
}
