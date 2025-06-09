package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Fuente;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import java.util.Set;

public interface IHechoRepository {
  public Fuente saveHechosDeFuente(Fuente fuente);
  public Fuente findById(Long id);

  Set<Fuente> findAll();

  Set<Hecho> findByIDFuente(Long idFuente);
}
