package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;

public interface IUserRepository {
  public Usuario findById(Long idUsuario);
}
