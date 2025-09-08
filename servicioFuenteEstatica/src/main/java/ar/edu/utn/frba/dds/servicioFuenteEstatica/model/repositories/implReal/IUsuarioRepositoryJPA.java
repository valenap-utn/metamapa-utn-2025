package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositoryJPA extends JpaRepository<Usuario, Long> {
}
