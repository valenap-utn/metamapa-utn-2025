package ar.edu.utn.frba.dds.clienteInterfaz.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoColeccion {
  private List<ColeccionDTOOutput> colecciones;
}
