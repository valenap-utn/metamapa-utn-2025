package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.List;

public interface IHechoRepository {

  List<Hecho> findAll();
  Hecho findById(Long id);

  Hecho saveHecho(Hecho hecho);
  List<Hecho> findByOrigen(Origen origen);

  List<Hecho> findByOrigenWithFiltros(Origen origen, FiltroDTO filtro);

  List<Hecho> findByNormalizado(boolean b);

  List<Hecho> findByFullTextSearch(String titulo);
}
