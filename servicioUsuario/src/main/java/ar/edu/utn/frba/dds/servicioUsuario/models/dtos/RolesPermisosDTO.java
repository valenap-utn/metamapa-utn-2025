package ar.edu.utn.frba.dds.servicioUsuario.models.dtos;

import java.util.List;
import lombok.Data;

@Data
public class RolesPermisosDTO {
  private String Rol;
  private List<String> Permisos;
}
