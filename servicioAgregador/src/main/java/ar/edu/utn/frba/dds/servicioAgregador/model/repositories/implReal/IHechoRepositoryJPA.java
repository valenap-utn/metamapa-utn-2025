package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long>, JpaSpecificationExecutor<Hecho> {
  List<Hecho> findByOrigen(Origen origen);


  Hecho findByIdExternoAndOrigen(Long idExterno, Origen origen);

  List<Hecho> findByNormalizado(boolean b);

  List<Hecho> findAllByEliminado(Boolean aFalse);
}
