package ar.edu.utn.frba.dds.servicioAgregador.model.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ConjuntoHechoCompleto {
  List<HechoDTOCompleto> hechos;
  List<String> categorias; //Categorias que se pueden mostrar en la vista de busqueda (filtrado)
  //Por ejemplo para: crear-coleccion || nav-hechos || subir-hecho
}
