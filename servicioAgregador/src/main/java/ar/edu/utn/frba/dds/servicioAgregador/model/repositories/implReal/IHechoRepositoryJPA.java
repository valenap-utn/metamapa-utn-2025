package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long> {
  List<Hecho> findByOrigen(Origen origen);

}
