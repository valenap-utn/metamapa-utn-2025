package ar.edu.utn.frba.dds.servicioFuenteDinamica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioFuenteDinamica.model.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepositoryJPA extends JpaRepository<Categoria, Long> {

}
