package ar.edu.utn.frba.dds.servicioAgregador.model.repositories;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {
  private final Map<Long, Usuario> usuariosByID;

  public UserRepository() {
    this.usuariosByID = new HashMap<>();
  }

  @Override
  public Usuario save(Usuario usuario) {
    return this.usuariosByID.put(usuario.getId(), usuario);
  }

  @Override
  public Usuario findById(Long idUsuario) {

    return this.usuariosByID.get(idUsuario);
  }

  @Override
  public Set<Usuario> findAll() {
    return new HashSet<Usuario>(this.usuariosByID.values());
  }
}
