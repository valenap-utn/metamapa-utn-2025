package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

  @Override
  public Usuario save(Usuario usuario) {
    return null;
  }

  @Override
  public Usuario findById(Long idUsuario) {
    return null;
  }

  @Override
  public Set<Usuario> findAll() {
    return Set.of();
  }
}
