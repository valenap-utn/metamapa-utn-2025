package ar.edu.utn.frba.dds.servicioUsuario.models.repositories;


import ar.edu.utn.frba.dds.servicioUsuario.models.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByEmail(String email);
}
