package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories;


import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Usuario;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {
  private final Map<Long, Usuario> usuariosByID;
  private final AtomicLong idCounter = new AtomicLong(1);
  public UserRepository() {
    this.usuariosByID = new HashMap<>();
  }

  @Override
  public Usuario save(Usuario usuario) {
    Long id = usuario.getId();
    if(id == null) {
      id = idCounter.getAndIncrement();
      usuario.setId(id);
    }
    return this.usuariosByID.put(id, usuario);
  }

  @Override
  public Usuario findById(Long idUsuario) {

    return this.usuariosByID.get(idUsuario);
  }

  @Override
  public Set<Usuario> findAll() {
    return new HashSet<>(this.usuariosByID.values());
  }
}
