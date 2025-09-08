package ar.edu.utn.frba.dds.servicioFuenteEstatica.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioFuenteEstatica.model.entities.Hecho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long> {
}
