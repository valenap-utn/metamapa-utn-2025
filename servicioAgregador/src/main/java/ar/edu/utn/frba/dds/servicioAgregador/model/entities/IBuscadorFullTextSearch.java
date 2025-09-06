package ar.edu.utn.frba.dds.servicioAgregador.model.entities;

import java.util.List;

public interface IBuscadorFullTextSearch {
  String crearNombreNormalizadoCon(String nombreCategoria, List<String> categoriasHechos);
}
