package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos.UsuarioDTO;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import jakarta.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Table(name = "hecho")
public class Hecho implements Revisable{
    @Column(nullable = false, name = "titulo")
    @Setter @Getter private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Setter @Getter private String descripcion;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id", nullable = false, name = "categoria_id")
    @Setter @Getter private Categoria categoria;

    @Embedded
    @Setter @Getter private Ubicacion ubicacion;

    @Column(name = "fecha_acontecimiento", nullable = false)
    @Setter @Getter private LocalDateTime fechaAcontecimiento;

    @Column(name = "fecha_carga", nullable = false)
    @Setter @Getter private LocalDateTime fechaCarga;

    @Column(name = "eliminado")
    @Setter @Getter private boolean eliminado;

    @Embedded
    @Setter @Getter private ContenidoMultimedia contenidoMultimedia;

    @ElementCollection
    @CollectionTable(name = "etiquetas_hecho", joinColumns =
    @JoinColumn(name = "hecho_id", referencedColumnName = "id"))
    @Getter private final Set<String> etiquetas;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    @Getter @Setter private Estado estado;

    @Column(name = "comentario_revision")
    @Setter private String comentarioRevision;

    @Column(name = "contenido")
    @Setter @Getter private String contenido;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter private Long id;

    @Column(name = "es_anonimo")
    @Setter @Getter private Boolean esAnonimo = false;

    @ManyToOne(fetch = FetchType.EAGER) // o .LAZY
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @Setter @Getter private Usuario usuario;

    public Hecho() {
        this.fechaCarga = LocalDateTime.now();
        this.eliminado = false;
        this.etiquetas = new HashSet<>();
    }

    public void agregarUsuario(Usuario usuario) {
        this.usuario = usuario;
        if (usuario == null) {
            this.esAnonimo = true;
        }
    }

    public void agregarEtiquetas(String ... etiquetas) {
        this.etiquetas.addAll(List.of(etiquetas));
    }

    public Long getIdUsuario() {
        return this.usuario != null ? this.usuario.getId(): null;
    }

  public boolean tieneId(Long id) {
      return this.id != null && this.id.equals(id);
  }

    public UsuarioDTO getUsuarioDTO() {
        return this.usuario == null? null: this.usuario.getUsuarioDTO();
    }
}
