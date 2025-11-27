package ar.edu.utn.frba.dds.servicioAgregador.model.repositories.specifications;

import ar.edu.utn.frba.dds.servicioAgregador.model.dtos.FiltroDTO;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Categoria;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.Hecho;
import ar.edu.utn.frba.dds.servicioAgregador.model.entities.origenes.Origen;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class HechoSpecification {
  private static final String ORIGEN = "origen_id";
  private static final String FECHAACONTECIMIENTO = "fechaAcontecimiento";
  private static final String FECHACARGA = "fechaCarga";
  private static final String CATEGORIA = "categoria_id";
  private static final String CATEGORIANOMBRE = "nombre";
  private static final String ELIMINADO = "eliminado";
  public static Specification<Hecho> filterBy(FiltroDTO filtro, Origen origen) {
    return Specification
            .where(tieneUrlOrigen(origen))
            .and(tieneTipoOrigen(origen))
            .and(noEstaEliminado())
            .and(tieneFechaCargaInicio(filtro.getFecha_reporte_desde()))
            .and(tieneFechaCargaFin(filtro.getFecha_reporte_hasta()))
            .and(tieneFechaAcontecimientoInicio(filtro.getFecha_acontecimiento_desde()))
            .and(tieneFechaAcontecimientoFin(filtro.getFecha_acontecimiento_hasta()))
            .and(tieneCategoria(filtro.getCategoria()))
            .and(tieneUsuario(filtro.getIdUsuario()));
  }

  private static Specification<Hecho> tieneUsuario(Long idUsuario) {
    return ((root, query, cb) -> {
        if (idUsuario == null) {
          return cb.conjunction();
        }
        Path<Origen> usuario = root.get("usuario");
        return cb.equal(usuario.get("id"), idUsuario);
      });
  }

  private static Specification<Hecho> noEstaEliminado() {
    return ((root, query, cb) -> cb.equal(root.get(HechoSpecification.ELIMINADO), Boolean.FALSE));
  }
  private static Specification<Hecho> tieneUrlOrigen(Origen origen) {
    return ((root, query, cb) -> {
     if (origen == null) {
      return cb.conjunction();
     }
     Path<Origen> origen1 = root.get("origen");
     return cb.equal(origen1.get("url"), origen.getUrl());
    });
  }

  private static Specification<Hecho> tieneTipoOrigen(Origen origen) {
    return ((root, query, cb) -> {
      if (origen == null) {
        return cb.conjunction();
      }
      Path<Origen> origen1 = root.get("origen");
      return cb.equal(origen1.get("tipo"), origen.getTipo());
    });
  }

  private static Specification<Hecho> tieneCategoria(String categoria) {
    return ((root, query, cb) -> {
      if(categoria == null || categoria.isEmpty())
        return cb.conjunction();

      Join<Hecho, Categoria> categoriaJoin = root.join(HechoSpecification.CATEGORIA, JoinType.INNER);
      return cb.equal(categoriaJoin.get(HechoSpecification.CATEGORIANOMBRE), categoriaJoin);
    });
  }

  private static Specification<Hecho> tieneFechaAcontecimientoFin(LocalDateTime fechaAcontecimientoHasta) {
    return ((root, query, cb) -> fechaAcontecimientoHasta == null ? cb.conjunction() : cb.lessThan(root.get(HechoSpecification.FECHAACONTECIMIENTO), fechaAcontecimientoHasta));
  }

  private static Specification<Hecho> tieneFechaAcontecimientoInicio(LocalDateTime fechaAcontecimientoDesde) {
    return ((root, query, cb) -> fechaAcontecimientoDesde == null ? cb.conjunction() : cb.greaterThan(root.get(HechoSpecification.FECHAACONTECIMIENTO), fechaAcontecimientoDesde));
  }

  private static Specification<Hecho> tieneFechaCargaFin(LocalDateTime fechaReporteHasta) {
    return ((root, query, cb) -> fechaReporteHasta == null ? cb.conjunction() : cb.equal(root.get(HechoSpecification.FECHACARGA), fechaReporteHasta));
  }

  private static Specification<Hecho> tieneFechaCargaInicio(LocalDateTime fechaReporteDesde) {
    return ((root, query, cb) -> fechaReporteDesde == null ? cb.conjunction() : cb.equal(root.get(HechoSpecification.FECHACARGA), fechaReporteDesde));
  }
}
