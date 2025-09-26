package ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "id_eliminado")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IDAPI {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "id_eliminado")
  private Long idEliminado;
  @Column(name = "nombre_API")
  private String nombreAPI;
}
