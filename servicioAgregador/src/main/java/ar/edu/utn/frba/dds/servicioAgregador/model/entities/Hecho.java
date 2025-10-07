package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.algoritmosConsenso.AlgoritmoConsenso;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import ar.edu.utn.frba.dds.servicioAgregador.model.repositories.converters.AlgoritmoConsensoConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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


@Entity
@Table(name = "hecho")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hecho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Column(nullable = false, name = "id_externo")
    @Getter @Setter
    private Long idExterno;

    @Column(nullable = false, name = "titulo")
    @Setter @Getter private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Setter @Getter private String descripcion;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false, name = "categoria_id")
    @Setter @Getter private Categoria categoria;

    @Embedded
    @Setter @Getter private Ubicacion ubicacion;

    @Column(name = "fecha_acontecimiento", nullable = false)
    @Setter @Getter private LocalDateTime fechaAcontecimiento;

    @Column(name = "fecha_carga", nullable = false)
    @Setter @Getter private LocalDateTime fechaCarga;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origen_id", referencedColumnName = "id", nullable = false)
    @Setter @Getter private Origen origen;

    @Column(name = "eliminado", nullable = false)
    @Builder.Default
    @Setter @Getter private Boolean eliminado = false;

    @Embedded
    @Setter @Getter private ContenidoMultimedia contenidoMultimedia;

    @ElementCollection
    @CollectionTable(name = "algoritmos_hecho", joinColumns =
    @JoinColumn(name = "hecho_id", referencedColumnName = "id"))
    @Convert(converter = AlgoritmoConsensoConverter.class)
    @Getter private final List<AlgoritmoConsenso> algosAceptados = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "etiquetas_hecho", joinColumns =
    @JoinColumn(name = "hecho_id", referencedColumnName = "id"))
    @Getter private final Set<String> etiquetas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, referencedColumnName = "id")
    @Getter @Setter private Usuario usuario;

    @Column(name = "normalizado")
    @Builder.Default
    @Getter private Boolean normalizado = false;


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


  public Long getIdUsuario() {
        return this.usuario.getId();
  }

    public String getClientNombre() {
        return this.origen.getNombreAPI();
    }

    public void marcarComoNormalizado() {
        this.normalizado = true;
    }

  public String getNombreCategoria() {
        return this.categoria.getNombre();
  }

  public void setDireccion(Direccion direccion) {
      this.ubicacion.setDireccion(direccion);
  }

  public Direccion getDireccion() {
      return this.ubicacion.getDireccion();
  }

  public boolean isEliminado() {
      return this.eliminado;
  }
}
