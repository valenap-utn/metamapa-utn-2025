package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.dtos;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Permiso;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.roles.Rol;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {
  private Long id;
  private String email;
  private String nombre;
  private String apellido;
  private Rol rol;
  private List<Permiso> permisos;
}
