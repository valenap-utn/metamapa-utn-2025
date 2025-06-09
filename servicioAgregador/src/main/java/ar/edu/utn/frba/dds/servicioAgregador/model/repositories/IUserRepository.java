package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Coleccion;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import java.util.Set;

public interface IUserRepository {
  public Usuario save(Usuario usuario);
  public Usuario findById(Long idUsuario);

  Set<Usuario> findAll();
}
