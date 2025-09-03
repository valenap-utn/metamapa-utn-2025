package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.enums.Estado;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import lombok.Getter;
import lombok.Setter;

public class Hecho implements Revisable{
    @Setter @Getter private String titulo;
    @Setter @Getter private String descripcion;
    @Setter @Getter private Categoria categoria;
    @Setter @Getter private Ubicacion ubicacion;
    @Setter @Getter private LocalDateTime fechaAcontecimiento;
    @Setter @Getter private LocalDateTime fechaCarga;
    @Setter @Getter private Origen origen;
    @Setter @Getter private boolean eliminado;
    @Setter @Getter private ContenidoMultimedia contenidoMultimedia;
    @Getter private final Set<String> etiquetas;
    @Getter @Setter private Estado estado;
    @Setter private String comentarioRevision;
    @Setter @Getter private String contenido;
    @Setter @Getter private Long id;
    @Setter @Getter private boolean esAnonimo;
    @Setter @Getter private Usuario usuario;

    public Hecho() {
        this.fechaCarga = LocalDateTime.now();
        this.eliminado = false;
        this.etiquetas = new HashSet<>();
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
}
