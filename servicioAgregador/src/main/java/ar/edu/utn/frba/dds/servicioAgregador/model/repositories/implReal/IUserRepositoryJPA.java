package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;


import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepositoryJPA extends JpaRepository<Usuario, Long> {
}
