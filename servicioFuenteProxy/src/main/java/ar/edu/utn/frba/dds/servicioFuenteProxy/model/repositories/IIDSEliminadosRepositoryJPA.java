package ar.edu.utn.frba.dds.servicioFuenteProxy.model.repositories;

import ar.edu.utn.frba.dds.servicioFuenteProxy.model.entities.IDAPI;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIDSEliminadosRepositoryJPA extends JpaRepository<IDAPI, Long> {
}
