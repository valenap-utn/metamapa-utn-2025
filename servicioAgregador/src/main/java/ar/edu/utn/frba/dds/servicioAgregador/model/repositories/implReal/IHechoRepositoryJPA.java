package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.implReal;

import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;
import reactor.util.annotation.NonNullApi;

@Repository
public interface IHechoRepositoryJPA extends JpaRepository<Hecho, Long>, JpaSpecificationExecutor<Hecho> {
  List<Hecho> findByOrigen(Origen origen);




}
