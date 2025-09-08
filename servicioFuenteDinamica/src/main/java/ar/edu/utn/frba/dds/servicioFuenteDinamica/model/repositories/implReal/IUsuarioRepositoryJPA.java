package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
}
